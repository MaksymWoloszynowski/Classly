package org.edziennik.gradeservice.grade.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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
@Table(name = "grades")
public class Grade {
    @Id
    @UuidGenerator
    private UUID id;

    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("100.0")
    private double grade;

    @NotNull
    private LocalDate date;

    private String description;

    @NotNull
    private UUID classificationPeriod;

    @NotNull
    @Enumerated(EnumType.STRING)
    private GradeType type;

    @NotNull
    @Min(0)
    @Max(3)
    private int weight;

    @NotNull
    private UUID studentId;

    @NotNull
    private UUID subjectId;
}
