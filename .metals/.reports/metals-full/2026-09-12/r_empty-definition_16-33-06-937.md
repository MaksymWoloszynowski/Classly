error id: file://<WORKSPACE>/backend/grade-service/src/main/java/org/edziennik/gradeservice/grade/mapper/GradeMapper.java:_empty_/`<any>`#date#studentId#
file://<WORKSPACE>/backend/grade-service/src/main/java/org/edziennik/gradeservice/grade/mapper/GradeMapper.java
empty definition using pc, found symbol in pc: _empty_/`<any>`#date#studentId#
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 1182
uri: file://<WORKSPACE>/backend/grade-service/src/main/java/org/edziennik/gradeservice/grade/mapper/GradeMapper.java
text:
```scala
package org.edziennik.gradeservice.grade.mapper;

import org.edziennik.gradeservice.grade.dto.GradeRequestDTO;
import org.edziennik.gradeservice.grade.dto.GradeResponseDTO;
import org.edziennik.gradeservice.grade.dto.GradeSummaryDTO;
import org.edziennik.gradeservice.grade.entity.Grade;
import org.edziennik.gradeservice.gradeCategory.entity.GradeCategory;

import java.time.LocalDate;

public class GradeMapper {
    public static GradeResponseDTO toDTO(Grade grade) {

        return GradeResponseDTO.builder()
                .id(grade.getId())
                .categoryId(grade.getGradeCategory().getId())
                .grade(grade.getGrade())
                .date(grade.getDate())
                .description(grade.getGradeCategory().getDescription())
                .type(grade.getGradeCategory().getType())
                .weight(grade.getGradeCategory().getWeight())
                .build();
    }

    public static Grade toModel(GradeRequestDTO gradeDTO, GradeCategory gradeCategory) {
        return Grade.builder()
                .gradeCategory(gradeCategory)
                .grade(gradeDTO.getGrade())
                .date(LocalDate.now())
                .@@studentId(gradeDTO.getStudentId())
                .build();
    }

    public static GradeSummaryDTO toSummaryDTO(Grade grade) {
        return GradeSummaryDTO.builder()
                .id(grade.getId())
                .grade(grade.getGrade())
                .date(grade.getDate())
                .studentId(grade.getStudentId())
                .build();
    }
}

```


#### Short summary: 

empty definition using pc, found symbol in pc: _empty_/`<any>`#date#studentId#