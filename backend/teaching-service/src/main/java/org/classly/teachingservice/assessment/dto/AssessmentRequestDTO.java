package org.classly.teachingservice.assessment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.classly.teachingservice.assessment.entity.AssessmentType;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Schema(description = "Data required to create or update an assessment")
public class AssessmentRequestDTO {
    @NotNull
    @Schema(description = "Teaching assignment ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private UUID teachingAssignmentId;

    @NotNull
    @Schema(description = "Assessment due date", example = "2026-10-15", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDate dateDue;

    @NotNull
    @Schema(description = "Assessment type", requiredMode = Schema.RequiredMode.REQUIRED)
    private AssessmentType type;

    @Schema(description = "Assessment description", example = "Chapter 1 test")
    private String description;
}