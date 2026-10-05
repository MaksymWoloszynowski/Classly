package org.classly.gradeservice.grade.dto;

import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Schema(description = "Data required to create or update an individual grade")
public class GradeRequestDTO {
    @NotNull
    @Schema(description = "Identifier of the grade category", requiredMode = Schema.RequiredMode.REQUIRED)
    private UUID gradeCategoryId;

    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("100.0")
    @Schema(description = "Grade value from 0 to 100", example = "85.5", minimum = "0", maximum = "100", requiredMode = Schema.RequiredMode.REQUIRED)
    private double grade;

    @NotNull
    @Schema(description = "Identifier of the student receiving the grade", requiredMode = Schema.RequiredMode.REQUIRED)
    private UUID studentId;
}