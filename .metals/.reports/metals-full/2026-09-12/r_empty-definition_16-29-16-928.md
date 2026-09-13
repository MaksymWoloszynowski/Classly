error id: file://<WORKSPACE>/backend/grade-service/src/main/java/org/edziennik/gradeservice/grade/entity/Grade.java:org/edziennik/gradeservice/gradeCategory/entity/GradeCategory#
file://<WORKSPACE>/backend/grade-service/src/main/java/org/edziennik/gradeservice/grade/entity/Grade.java
empty definition using pc, found symbol in pc: org/edziennik/gradeservice/gradeCategory/entity/GradeCategory#
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 193
uri: file://<WORKSPACE>/backend/grade-service/src/main/java/org/edziennik/gradeservice/grade/entity/Grade.java
text:
```scala
package org.edziennik.gradeservice.grade.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.edziennik.gradeservice.gradeCategory.entity.@@GradeCategory;
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

```


#### Short summary: 

empty definition using pc, found symbol in pc: org/edziennik/gradeservice/gradeCategory/entity/GradeCategory#