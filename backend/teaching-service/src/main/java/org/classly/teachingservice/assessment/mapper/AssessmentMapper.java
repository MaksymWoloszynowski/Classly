package org.classly.teachingservice.assessment.mapper;

import org.classly.teachingservice.assessment.dto.AssessmentRequestDTO;
import org.classly.teachingservice.assessment.dto.AssessmentResponseDTO;
import org.classly.teachingservice.assessment.entity.Assessment;

import java.time.LocalDate;

public class AssessmentMapper {
    public static AssessmentResponseDTO toDTO(Assessment assessment) {
        return AssessmentResponseDTO.builder()
                .id(assessment.getId())
                .teachingAssignmentId(assessment.getTeachingAssignmentId())
                .dateMade(assessment.getDateMade())
                .dateDue(assessment.getDateDue())
                .type(assessment.getType())
                .description(assessment.getDescription())
                .build();
    }

    public static Assessment toModel(AssessmentRequestDTO dto) {
        return Assessment.builder()
                .teachingAssignmentId(dto.getTeachingAssignmentId())
                .dateMade(LocalDate.now())
                .dateDue(dto.getDateDue())
                .type(dto.getType())
                .description(dto.getDescription())
                .build();
    }
}