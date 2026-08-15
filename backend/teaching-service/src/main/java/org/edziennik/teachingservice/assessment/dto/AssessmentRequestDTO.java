package org.edziennik.teachingservice.assessment.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.edziennik.teachingservice.assessment.entity.AssessmentType;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class AssessmentRequestDTO {
    @NotNull
    private UUID teachingAssignmentId;

    @NotNull
    private UUID groupId;

    @NotNull
    private LocalDate dateDue;

    @NotNull
    private AssessmentType type;

    private String description;
}