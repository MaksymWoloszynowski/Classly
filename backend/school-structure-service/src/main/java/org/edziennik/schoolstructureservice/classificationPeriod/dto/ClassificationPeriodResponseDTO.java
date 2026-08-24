package org.edziennik.schoolstructureservice.classificationPeriod.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.edziennik.schoolstructureservice.student.dto.StudentSummaryDTO;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClassificationPeriodResponseDTO {
    private UUID id;
    private int semester;
    private LocalDate dateFrom;
    private LocalDate dateTo;
}
