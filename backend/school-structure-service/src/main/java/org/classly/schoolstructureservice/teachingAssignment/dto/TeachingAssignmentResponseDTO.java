package org.classly.schoolstructureservice.teachingAssignment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Individual teaching assignment returned by the service")
public class TeachingAssignmentResponseDTO {
    @Schema(description = "Teaching assignment ID")
    private UUID id;

    @Schema(description = "Teacher ID")
    private UUID teacherId;

    @Schema(description = "Teacher name")
    private String teacherName;

    @Schema(description = "Subject ID")
    private UUID subjectId;

    @Schema(description = "Subject name")
    private String subjectName;

    @Schema(description = "Group ID")
    private UUID groupId;

    @Schema(description = "Group name")
    private String groupName;
}
