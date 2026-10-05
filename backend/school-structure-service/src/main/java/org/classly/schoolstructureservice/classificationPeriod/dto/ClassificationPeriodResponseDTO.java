package org.classly.schoolstructureservice.classificationPeriod.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Classification period response returned by the service")
public class ClassificationPeriodResponseDTO {
    @Schema(description = "Classification period ID")
    private UUID id;

    @Schema(description = "Classification period semester")
    private int semester;

    @Schema(description = "Classification period starting date")
    private LocalDate dateFrom;

    @Schema(description = "Classification period ending date")
    private LocalDate dateTo;
}
