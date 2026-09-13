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
    private UUID gradeCategoryId;

    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("100.0")
    private double grade;

    @NotNull
    private UUID studentId;
}