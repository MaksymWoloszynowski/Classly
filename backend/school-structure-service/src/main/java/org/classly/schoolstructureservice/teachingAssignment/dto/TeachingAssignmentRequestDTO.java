package org.classly.schoolstructureservice.teachingAssignment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Schema(description = "Data required to create or update a teaching assignment")
public class TeachingAssignmentRequestDTO {
    @NotNull
    @Schema(description = "Teacher ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private UUID teacherId;

    @NotNull
    @Schema(description = "Subject ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private UUID subjectId;

    @NotNull
    @Schema(description = "Group ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private UUID groupId;
}
