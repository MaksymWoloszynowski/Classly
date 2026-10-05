package org.classly.gradeservice.semesterGrade.dto;

import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.classly.gradeservice.semesterGrade.entity.SemesterGradeType;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Data required to create or update a semester grade")
public class SemesterGradeRequestDTO {
    @NotNull
    @Min(1)
    @Max(6)
    @Schema(description = "School grade from 1 to 6", example = "5", minimum = "1", maximum = "6", requiredMode = Schema.RequiredMode.REQUIRED)
    private int grade;

    @NotNull
    @Enumerated
    @Schema(description = "Semester grade type", requiredMode = Schema.RequiredMode.REQUIRED)
    private SemesterGradeType type;

    @NotNull
    @Schema(description = "Student identifier", requiredMode = Schema.RequiredMode.REQUIRED)
    private UUID studentId;

    @NotNull
    @Schema(description = "Classification period identifier", requiredMode = Schema.RequiredMode.REQUIRED)
    private UUID classificationPeriod;

    @NotNull
    @Schema(description = "Teaching assignment identifier", requiredMode = Schema.RequiredMode.REQUIRED)
    private UUID teachingAssignmentId;
}