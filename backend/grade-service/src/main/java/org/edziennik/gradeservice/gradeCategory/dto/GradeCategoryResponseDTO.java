package org.edziennik.gradeservice.gradeCategory.dto;

import lombok.*;
import org.edziennik.gradeservice.grade.dto.GradeSummaryDTO;
import org.edziennik.gradeservice.grade.entity.GradeType;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GradeCategoryResponseDTO {
    private UUID id;
    private String description;
    private int weight;
    private GradeType type;
    private List<GradeSummaryDTO> grades;
}
