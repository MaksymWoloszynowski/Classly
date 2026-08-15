package org.edziennik.gradeservice.grade.dto;

import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.edziennik.gradeservice.grade.entity.GradeType;

import java.util.UUID;

@Getter
@Setter
public class GradeRequestDTO {
    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("100.0")
    private double grade;

    @NotNull
    @Enumerated
    private GradeType type;

    @NotNull
    @Min(1)
    @Max(2)
    private int semester;

    private String description;

    @NotNull
    @Min(0)
    @Max(3)
    private int weight;

    @NotNull
    private UUID studentId;

    @NotNull
    private UUID subjectId;

    @NotNull
    private String subjectName;
}