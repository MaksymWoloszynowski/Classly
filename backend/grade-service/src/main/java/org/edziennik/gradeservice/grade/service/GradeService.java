package org.edziennik.gradeservice.grade.service;

import org.edziennik.gradeservice.grade.dto.GradeRequestDTO;
import org.edziennik.gradeservice.grade.dto.GradeResponseDTO;
import org.edziennik.gradeservice.grade.exception.GradeNotFoundException;
import org.edziennik.gradeservice.grade.mapper.GradeMapper;
import org.edziennik.gradeservice.grade.entity.Grade;
import org.edziennik.gradeservice.grade.repository.GradeRepository;
import org.edziennik.gradeservice.grpc.SchoolStructureGrpcClient;
import org.edziennik.schoolstructureservice.grpc.*;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GradeService {
    private final SchoolStructureGrpcClient schoolStructureClient;
    private final GradeRepository gradeRepository;

    public GradeService(GradeRepository gradeRepository, SchoolStructureGrpcClient schoolStructureClient) {
        this.gradeRepository = gradeRepository;
        this.schoolStructureClient = schoolStructureClient;
    }

    public List<GradeResponseDTO> getGrades(UUID studentId, UUID groupId, UUID subjectId, UUID classificationPeriod) {
        Specification<Grade> spec = Specification.allOf();

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

        return mapToDTOList(gradeRepository.findAll(spec));
    }

    public GradeResponseDTO getGradeById(UUID gradeId) {
        return mapToDTO(getGrade(gradeId));
    }

    public GradeResponseDTO createGrade(GradeRequestDTO gradeRequestDTO) {
        Grade newGrade = gradeRepository.save(GradeMapper.toModel(gradeRequestDTO));

        return mapToDTO(newGrade);
    }

    public GradeResponseDTO updateGrade(UUID gradeId, GradeRequestDTO gradeRequestDTO) {
        Grade grade = getGrade(gradeId);

        grade.setGrade(gradeRequestDTO.getGrade());
        grade.setDescription(gradeRequestDTO.getDescription());
        grade.setWeight(gradeRequestDTO.getWeight());
        grade.setType(gradeRequestDTO.getType());

        Grade updated = gradeRepository.save(grade);

        return mapToDTO(updated);
    }

    public void deleteGrade(UUID id) {
        gradeRepository.delete(getGrade(id));
    }

    private GradeResponseDTO mapToDTO(Grade grade) {
        StudentResponse studentResponse = schoolStructureClient.getStudent(grade.getStudentId());
        SubjectResponse subjectResponse = schoolStructureClient.getSubject(grade.getSubjectId());

        GradeResponseDTO responseDTO = GradeMapper.toDTO(grade);

        responseDTO.setSubjectName(subjectResponse.getName());
        responseDTO.setStudentFullName(studentResponse.getFirstName() + " " + studentResponse.getLastName());

        return responseDTO;
    }

    private List<GradeResponseDTO> mapToDTOList(List<Grade> grades) {
        Set<UUID> studentIds = grades.stream().map(Grade::getStudentId).collect(Collectors.toSet());
        Set<UUID> subjectIds = grades.stream().map(Grade::getSubjectId).collect(Collectors.toSet());

        Map<UUID, String> studentNames = schoolStructureClient.getStudentNames(studentIds);
        Map<UUID, String> subjectNames = schoolStructureClient.getSubjectNames(subjectIds);

        return grades.stream().map(grade -> {
            GradeResponseDTO dto = GradeMapper.toDTO(grade);
            dto.setStudentFullName(studentNames.get(grade.getStudentId()));
            dto.setSubjectName(subjectNames.get(grade.getSubjectId()));
            return dto;
        }).collect(Collectors.toList());
    }

    private Grade getGrade(UUID gradeId) {
        return gradeRepository.findById(gradeId)
                .orElseThrow(() -> new GradeNotFoundException("Grade not found with ID: " + gradeId));
    }
}