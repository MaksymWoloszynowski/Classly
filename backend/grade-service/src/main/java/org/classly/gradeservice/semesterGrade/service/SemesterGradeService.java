package org.classly.gradeservice.semesterGrade.service;

import org.classly.gradeservice.grpc.SchoolStructureGrpcClient;
import org.classly.gradeservice.semesterGrade.dto.SemesterGradeRequestDTO;
import org.classly.gradeservice.semesterGrade.dto.SemesterGradeResponseDTO;
import org.classly.gradeservice.semesterGrade.entity.SemesterGrade;
import org.classly.gradeservice.semesterGrade.exception.SemesterGradeNotFoundException;
import org.classly.gradeservice.semesterGrade.mapper.SemesterGradeMapper;
import org.classly.gradeservice.semesterGrade.repository.SemesterGradeRepository;
import org.classly.schoolstructureservice.grpc.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SemesterGradeService {
    private final SchoolStructureGrpcClient schoolStructureClient;
    private final SemesterGradeRepository semesterGradeRepository;

    public SemesterGradeService(SemesterGradeRepository semesterGradeRepository, SchoolStructureGrpcClient schoolStructureClient) {
        this.semesterGradeRepository = semesterGradeRepository;
        this.schoolStructureClient = schoolStructureClient;
    }

    public List<SemesterGradeResponseDTO> getGrades(UUID studentId, UUID groupId, UUID subjectId, UUID classificationPeriod) {
        Specification<SemesterGrade> spec = Specification.allOf();

        if (studentId != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("studentId"), studentId));
        }
        if (groupId != null) {
            List<UUID> studentIds = schoolStructureClient.getStudentIdsForGroup(groupId);
            spec = spec.and((root, query, cb) -> root.get("studentId").in(studentIds));
        }
        if (subjectId != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("subjectId"), subjectId));
        }
        if (classificationPeriod != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("classificationPeriod"), classificationPeriod));
        }

        return mapToDTOList(semesterGradeRepository.findAll(spec));
    }

    public SemesterGradeResponseDTO getGradeById(UUID gradeId) {
        SemesterGrade grade = getSemesterGrade(gradeId);

        return mapToDTO(grade);
    }

    public SemesterGradeResponseDTO createGrade(SemesterGradeRequestDTO gradeRequestDTO) {
        SemesterGrade newGrade = semesterGradeRepository.save(SemesterGradeMapper.toModel(gradeRequestDTO));

        return mapToDTO(newGrade);
    }

    public SemesterGradeResponseDTO updateGrade(UUID gradeId, SemesterGradeRequestDTO gradeRequestDTO) {
        SemesterGrade grade = getSemesterGrade(gradeId);

        grade.setGrade(gradeRequestDTO.getGrade());
        grade.setType(gradeRequestDTO.getType());
        grade.setStudentId(gradeRequestDTO.getStudentId());
        grade.setClassificationPeriod(gradeRequestDTO.getClassificationPeriod());

        SemesterGrade updated = semesterGradeRepository.save(grade);

        return mapToDTO(updated);
    }

    public void deleteGrade(UUID id) {
        SemesterGrade grade = getSemesterGrade(id);
        semesterGradeRepository.delete(grade);
    }

    private SemesterGradeResponseDTO mapToDTO(SemesterGrade grade) {
        StudentResponse studentResponse = schoolStructureClient.getStudent(grade.getStudentId());
        TeachingAssignmentResponse assignmentResponse = schoolStructureClient.getTeachingAssignment(grade.getTeachingAssignmentId());

        SemesterGradeResponseDTO responseDTO = SemesterGradeMapper.toDTO(grade);

        responseDTO.setStudentFullName(studentResponse.getFirstName() + " " + studentResponse.getLastName());
        responseDTO.setSubjectName(assignmentResponse.getSubject());

        return responseDTO;
    }

    private List<SemesterGradeResponseDTO> mapToDTOList(List<SemesterGrade> grades) {
        Set<UUID> studentIds = grades.stream().map(SemesterGrade::getStudentId).collect(Collectors.toSet());
        Set<UUID> groupIds = studentIds.stream().map(id -> UUID.fromString(schoolStructureClient.getStudent(id).getGroupId())).collect(Collectors.toSet());
        Set<UUID> teachingAssignmentsIds = groupIds.stream().map(schoolStructureClient::getTeachingAssignmentIdsByGroup).flatMap(List::stream).collect(Collectors.toSet());

        Map<UUID, StudentResponse> studentNames = schoolStructureClient.getStudents(studentIds);
        Map<UUID, TeachingAssignmentResponse> teachingAssignmentResponseMap = schoolStructureClient.getTeachingAssignments(teachingAssignmentsIds);

        return grades.stream().map(grade -> {
            SemesterGradeResponseDTO dto = SemesterGradeMapper.toDTO(grade);
            dto.setStudentFullName(studentNames.get(grade.getStudentId()).getFirstName()+ " " + studentNames.get(grade.getStudentId()).getLastName());
            dto.setSubjectName(teachingAssignmentResponseMap.get(grade.getTeachingAssignmentId()).getSubject());
            return dto;
        }).collect(Collectors.toList());
    }

    private SemesterGrade getSemesterGrade(UUID gradeId) {
        return semesterGradeRepository.findById(gradeId)
                .orElseThrow(() -> new SemesterGradeNotFoundException("Semester g ade not found with ID: " + gradeId));
    }
}
