package org.classly.schoolstructureservice.teacher.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.classly.schoolstructureservice.group.dto.GroupSummaryDTO;
import org.classly.schoolstructureservice.teachingAssignment.dto.TeachingAssignmentResponseDTO;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Individual teacher returned by the service")
public class AdminTeacherResponseDTO {
    @Schema(description = "Teacher ID")
    private UUID id;

    @Schema(description = "Teacher social ID")
    private String socialId;

    @Schema(description = "Teacher first name")
    private String firstName;

    @Schema(description = "Teacher last name")
    private String lastName;

    @Schema(description = "Teaching assignments assigned to the teacher")
    private List<TeachingAssignmentResponseDTO> teachingAssignments;

    @Schema(description = "Teacher homeroom groups")
    private List<GroupSummaryDTO> homeroomGroups;
}