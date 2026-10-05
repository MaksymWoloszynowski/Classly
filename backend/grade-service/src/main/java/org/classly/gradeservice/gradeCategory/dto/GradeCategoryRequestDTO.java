package org.classly.gradeservice.gradeCategory.dto;

import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.classly.gradeservice.grade.entity.GradeType;

import java.util.UUID;

@Getter
@Setter
@Schema(description = "Data required to create or update a grade category")
public class GradeCategoryRequestDTO {
    @NotNull
    @Enumerated
    @Schema(description = "Category type", requiredMode = Schema.RequiredMode.REQUIRED)
    private GradeType type;

    @NotNull
    @Schema(description = "Classification period identifier", requiredMode = Schema.RequiredMode.REQUIRED)
    private UUID classificationPeriod;

    @Schema(description = "Optional category description", example = "Chapter test")
    private String description;

    @NotNull
    @Min(0)
    @Max(3)
    @Schema(description = "Category weight from 0 to 3", example = "2", minimum = "0", maximum = "3", requiredMode = Schema.RequiredMode.REQUIRED)
    private int weight;

    @NotNull
    @Schema(description = "Teaching assignment identifier", requiredMode = Schema.RequiredMode.REQUIRED)
    private UUID teachingAssignmentId;
}
