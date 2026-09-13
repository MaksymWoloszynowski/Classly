package org.edziennik.gradeservice.gradeCategory.dto;

import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.edziennik.gradeservice.grade.entity.GradeType;

import java.util.UUID;

@Getter
@Setter
public class GradeCategoryRequestDTO {
    @NotNull
    @Enumerated
    private GradeType type;

    @NotNull
    private UUID classificationPeriod;

    private String description;

    @NotNull
    @Min(0)
    @Max(3)
    private int weight;

    @NotNull
    private UUID teachingAssignmentId;
}
