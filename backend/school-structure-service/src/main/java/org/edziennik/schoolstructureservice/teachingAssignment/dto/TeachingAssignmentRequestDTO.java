package org.edziennik.schoolstructureservice.teachingAssignment.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class TeachingAssignmentRequestDTO {
    @NotNull
    private UUID teacherId;

    @NotNull
    private UUID subjectId;

    @NotNull
    private UUID groupId;
}
