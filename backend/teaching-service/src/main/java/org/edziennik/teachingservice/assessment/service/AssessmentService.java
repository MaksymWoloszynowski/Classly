package org.edziennik.teachingservice.assessment.service;

import org.edziennik.schoolstructureservice.grpc.TeachingAssignmentResponse;
import org.edziennik.teachingservice.assessment.dto.AssessmentRequestDTO;
import org.edziennik.teachingservice.assessment.dto.AssessmentResponseDTO;
import org.edziennik.teachingservice.assessment.entity.Assessment;
import org.edziennik.teachingservice.assessment.exception.AssessmentNotFoundException;
import org.edziennik.teachingservice.assessment.mapper.AssessmentMapper;
import org.edziennik.teachingservice.assessment.repository.AssessmentRepository;
import org.edziennik.teachingservice.grpc.SchoolStructureGrpcClient;
import org.edziennik.teachingservice.session.dto.SessionResponseDTO;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class AssessmentService {
    private final AssessmentRepository assessmentRepository;
    private final SchoolStructureGrpcClient schoolStructureClient;

    public AssessmentService(AssessmentRepository assessmentRepository, SchoolStructureGrpcClient schoolStructureClient) {
        this.assessmentRepository = assessmentRepository;
        this.schoolStructureClient = schoolStructureClient;
    }

    public List<AssessmentResponseDTO> getAllAssessments(UUID teachingAssignmentId) {

        if (teachingAssignmentId != null) {
           mapToDTOList(assessmentRepository.findByTeachingAssignmentId(teachingAssignmentId));
        }

        return mapToDTOList(assessmentRepository.findAll());
    }

    public AssessmentResponseDTO getAssessmentById(UUID id) {
        return mapToDTO(getAssessment(id));
    }

    public List<AssessmentResponseDTO> getAssessmentsByGroupAndDate(UUID groupId, LocalDate from, LocalDate to) {
        List<UUID> teachingAssignmentIds = schoolStructureClient.getTeachingAssignmentIdsByGroup(groupId);

        return mapToDTOList(assessmentRepository.findByTeachingAssignmentIdInAndDateDueBetween(teachingAssignmentIds, from, to));
    }

    public AssessmentResponseDTO createAssessment(AssessmentRequestDTO dto) {
        Assessment saved = assessmentRepository.save(AssessmentMapper.toModel(dto));
        return mapToDTO(saved);
    }

    public AssessmentResponseDTO updateAssessment(UUID id, AssessmentRequestDTO dto) {
        Assessment assessment = getAssessment(id);

        assessment.setTeachingAssignmentId(dto.getTeachingAssignmentId());
        assessment.setDateDue(dto.getDateDue());
        assessment.setType(dto.getType());
        assessment.setDescription(dto.getDescription());

        Assessment updated = assessmentRepository.save(assessment);
        return mapToDTO(updated);
    }

    public void deleteAssessment(UUID id) {
        assessmentRepository.delete(getAssessment(id));
    }

    private AssessmentResponseDTO mapToDTO(Assessment assessment) {
        TeachingAssignmentResponse assignment = schoolStructureClient.getTeachingAssignment(assessment.getTeachingAssignmentId());

        AssessmentResponseDTO dto = AssessmentMapper.toDTO(assessment);
        dto.setSubjectName(assignment.getSubject());
        dto.setTeacherName(assignment.getTeacher());

        return dto;
    }

    private List<AssessmentResponseDTO> mapToDTOList(List<Assessment> assessments) {
        return assessments.stream().map(this::mapToDTO).toList();
    }

    private Assessment getAssessment(UUID id) {
        return assessmentRepository.findById(id)
                .orElseThrow(() -> new AssessmentNotFoundException("Assessment not found with ID: " + id));
    }
}