package org.edziennik.teachingservice.assessment.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Assessment {
    @Id
    @UuidGenerator
    private UUID id;

    @NotNull
    private UUID teachingAssignmentId;

    @NotNull
    private LocalDate dateMade;

    @NotNull
    private LocalDate dateDue;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AssessmentType type;

    private String description;
}