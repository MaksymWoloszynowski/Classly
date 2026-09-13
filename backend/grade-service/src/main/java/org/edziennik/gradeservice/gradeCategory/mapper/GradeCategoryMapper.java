package org.edziennik.gradeservice.gradeCategory.mapper;

import org.edziennik.gradeservice.gradeCategory.dto.GradeCategoryRequestDTO;
import org.edziennik.gradeservice.gradeCategory.dto.GradeCategoryResponseDTO;
import org.edziennik.gradeservice.gradeCategory.entity.GradeCategory;

import java.time.LocalDate;
import java.util.Collections;

public class GradeCategoryMapper {
    public static GradeCategoryResponseDTO toDTO(GradeCategory gradeCategory) {
        return GradeCategoryResponseDTO.builder()
                .id(gradeCategory.getId())
                .description(gradeCategory.getDescription())
                .weight(gradeCategory.getWeight())
                .type(gradeCategory.getType())
                .grades(Collections.emptyList())
                .build();
    }

    public static GradeCategory toModel(GradeCategoryRequestDTO requestDTO) {
        return GradeCategory.builder()
                .description(requestDTO.getDescription())
                .classificationPeriod(requestDTO.getClassificationPeriod())
                .type(requestDTO.getType())
                .weight(requestDTO.getWeight())
                .teachingAssignmentId(requestDTO.getTeachingAssignmentId())
                .build();
    }
}
