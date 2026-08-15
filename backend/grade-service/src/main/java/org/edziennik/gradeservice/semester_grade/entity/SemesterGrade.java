package org.edziennik.gradeservice.semester_grade.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "semester_grades")
public class SemesterGrade {
    @Id
    @UuidGenerator
    private UUID id;

    @NotNull
    @Min(1)
    @Max(6)
    private int grade;

    @NotNull
    @Enumerated(EnumType.STRING)
    private SemesterGradeType type;

    @NotNull
    private String schoolYear;

    @NotNull
    private UUID studentId;

    @NotNull
    private UUID subjectId;
}
