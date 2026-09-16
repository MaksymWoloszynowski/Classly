package org.edziennik.schoolstructureservice.teacher.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.edziennik.schoolstructureservice.group.dto.GroupResponseDTO;
import org.edziennik.schoolstructureservice.group.dto.GroupSummaryDTO;
import org.edziennik.schoolstructureservice.parent.dto.ParentSummaryDTO;
import org.edziennik.schoolstructureservice.teachingAssignment.dto.TeachingAssignmentResponseDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Individual teacher returned by the service")
public class TeacherResponseDTO {
    @Schema(description = "Teacher ID")
    private UUID id;

    @Schema(description = "Teacher first name")
    private String firstName;

    @Schema(description = "Teacher last name")
    private String lastName;

    @Schema(description = "Teaching assignments assigned to the teacher")
    private List<TeachingAssignmentResponseDTO> teachingAssignments;

    @Schema(description = "Teacher homeroom groups")
    private List<GroupSummaryDTO> homeroomGroups;
}