package org.edziennik.schoolstructureservice.classificationPeriod.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ClassificationPeriodRequestDTO {
    @NotBlank
    private int semester;

    @NotNull
    private LocalDate dateFrom;

    @NotNull
    private LocalDate dateTo;
}
