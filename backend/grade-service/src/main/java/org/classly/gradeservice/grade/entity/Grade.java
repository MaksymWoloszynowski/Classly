package org.classly.gradeservice.grade.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.classly.gradeservice.gradeCategory.entity.GradeCategory;
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

    @ManyToOne
    @JoinColumn(name = "grade_category_id")
    @NotNull
    private GradeCategory gradeCategory;

    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("100.0")
    private double grade;

    @NotNull
    private LocalDate date;

    @NotNull
    private UUID studentId;
}
