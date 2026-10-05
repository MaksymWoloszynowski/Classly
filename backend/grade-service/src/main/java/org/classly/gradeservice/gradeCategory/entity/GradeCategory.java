package org.classly.gradeservice.gradeCategory.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.classly.gradeservice.grade.entity.Grade;
import org.classly.gradeservice.grade.entity.GradeType;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "grade_categories")
public class GradeCategory {
    @Id
    @UuidGenerator
    private UUID id;

    private String description;

    @NotNull
    private UUID classificationPeriod;

    @NotNull
    @Enumerated(EnumType.STRING)
    private GradeType type;

    @NotNull
    private int weight;

    @NotNull
    private UUID teachingAssignmentId;

    @OneToMany(mappedBy = "gradeCategory")
    private List<Grade> grades;
}
