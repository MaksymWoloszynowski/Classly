package org.edziennik.schoolstructureservice.classificationPeriod.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "classification_periods")
public class ClassificationPeriod {
    @Id
    @UuidGenerator
    private UUID id;

    @NotBlank
    private int semester;

    @NotNull
    private LocalDate dateFrom;

    @NotNull
    private LocalDate dateTo;
}
