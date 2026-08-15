package org.edziennik.gradeservice.semester_grade.dto;

import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.edziennik.gradeservice.semester_grade.entity.SemesterGradeType;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class SemesterGradeRequestDTO {
    @NotNull
    @Min(1)
    @Max(6)
    private int grade;

    @NotNull
    @Enumerated
    private SemesterGradeType type;

    @NotNull
    private UUID studentId;

    @NotNull
    private String schoolYear;

    @NotNull
    private UUID subjectId;
}