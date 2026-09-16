package org.edziennik.gradeservice.gradeCategory.dto;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import org.edziennik.gradeservice.grade.dto.GradeSummaryDTO;
import org.edziennik.gradeservice.grade.entity.GradeType;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Grade category returned by the grade service")
public class GradeCategoryResponseDTO {
    @Schema(description = "Grade category identifier")
    private UUID id;

    @Schema(description = "Category description")
    private String description;

    @Schema(description = "Category weight from 0 to 3", example = "3", minimum = "0", maximum = "3")
    private int weight;

    @Schema(description = "Category type")
    private GradeType type;

    @Schema(description = "Grades assigned to this category")
    private List<GradeSummaryDTO> grades;
}
