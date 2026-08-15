package org.edziennik.gradeservice.semester_grade.service;

import net.devh.boot.grpc.client.inject.GrpcClient;
import org.edziennik.gradeservice.semester_grade.dto.SemesterGradeRequestDTO;
import org.edziennik.gradeservice.semester_grade.dto.SemesterGradeResponseDTO;
import org.edziennik.gradeservice.semester_grade.entity.SemesterGrade;
import org.edziennik.gradeservice.semester_grade.exception.SemesterGradeNotFoundException;
import org.edziennik.gradeservice.semester_grade.mapper.SemesterGradeMapper;
import org.edziennik.gradeservice.semester_grade.repository.SemesterGradeRepository;
import org.edziennik.schoolstructureservice.grpc.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SemesterGradeService {
    @GrpcClient("school-structure-service")
    private SchoolStructureServiceGrpc.SchoolStructureServiceBlockingStub blockingStub;
    private final SemesterGradeRepository semesterGradeRepository;

    public SemesterGradeService(SemesterGradeRepository semesterGradeRepository) {
        this.semesterGradeRepository = semesterGradeRepository;
    }

    public List<SemesterGradeResponseDTO> getAllGrades() {
        return semesterGradeRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
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
        grade.setSubjectId(gradeRequestDTO.getSubjectId());
        grade.setStudentId(gradeRequestDTO.getStudentId());
        grade.setSchoolYear(gradeRequestDTO.getSchoolYear());

        SemesterGrade updated = semesterGradeRepository.save(grade);

        return mapToDTO(updated);
    }

    public void deleteGrade(UUID id) {
        SemesterGrade grade = getSemesterGrade(id);
        semesterGradeRepository.delete(grade);
    }

    private StudentResponse getGrpcStudent(UUID studentId) {
        StudentRequest request = StudentRequest.newBuilder().setStudentId(studentId.toString()).build();
        return blockingStub.getStudent(request);
    }

    private SubjectResponse getGrpcSubject(UUID subjectId) {
        SubjectRequest request = SubjectRequest.newBuilder().setSubjectId(subjectId.toString()).build();
        return blockingStub.getSubject(request);
    }

    private List<UUID> getGrpcStudentsIdFromGroup(UUID groupId) {
        GroupRequest request = GroupRequest.newBuilder().setGroupId(groupId.toString()).build();

        StudentIdListResponse response = blockingStub.getStudentIdsByGroup(request);

        List<UUID> studentIds = response.getStudentIdsList().stream().map(UUID::fromString).toList();

        return studentIds;
    }

    private SemesterGradeResponseDTO mapToDTO(SemesterGrade grade) {
        StudentResponse studentResponse = getGrpcStudent(grade.getStudentId());
        SubjectResponse subjectResponse = getGrpcSubject(grade.getSubjectId());

        SemesterGradeResponseDTO responseDTO = SemesterGradeMapper.toDTO(grade);

        responseDTO.setSubjectName(subjectResponse.getName());
        responseDTO.setStudentFullName(studentResponse.getFirstName() + " " + studentResponse.getLastName());

        return responseDTO;
    }

    private SemesterGrade getSemesterGrade(UUID gradeId) {
        return semesterGradeRepository.findById(gradeId)
                .orElseThrow(() -> new SemesterGradeNotFoundException("Semester g ade not found with ID: " + gradeId));
    }
}
