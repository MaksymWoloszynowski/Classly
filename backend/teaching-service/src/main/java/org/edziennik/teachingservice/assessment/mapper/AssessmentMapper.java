package org.edziennik.teachingservice.assessment.mapper;

import org.edziennik.teachingservice.assessment.dto.AssessmentRequestDTO;
import org.edziennik.teachingservice.assessment.dto.AssessmentResponseDTO;
import org.edziennik.teachingservice.assessment.entity.Assessment;

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
                .dateDue(dto.getDateDue())
                .groupId(dto.getGroupId())
                .type(dto.getType())
                .description(dto.getDescription())
                .build();
    }
}