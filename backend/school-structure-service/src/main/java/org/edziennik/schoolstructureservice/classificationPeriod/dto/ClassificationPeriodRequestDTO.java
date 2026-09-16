package org.edziennik.schoolstructureservice.classificationPeriod.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Schema(description = "Data required to create or update a classification period")
public class ClassificationPeriodRequestDTO {
    @NotBlank
    @Schema(description = "Semester", requiredMode = Schema.RequiredMode.REQUIRED)
    private int semester;

    @NotNull
    @Schema(description = "Classification period starting date in ISO-8601 format", example = "2026-09-01", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDate dateFrom;

    @NotNull
    @Schema(description = "Classification period ending date in ISO-8601 format", example = "2026-12-31", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDate dateTo;
}
