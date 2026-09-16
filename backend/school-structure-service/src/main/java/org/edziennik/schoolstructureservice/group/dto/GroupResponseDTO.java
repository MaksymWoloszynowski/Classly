package org.edziennik.schoolstructureservice.group.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.edziennik.schoolstructureservice.student.dto.StudentSummaryDTO;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Group response returned by service")
public class GroupResponseDTO {
    @Schema(description = "Group ID")
    private UUID id;

    @Schema(description = "Group name")
    private String name;

    @Schema(description = "Students assigned to the group")
    private List<StudentSummaryDTO> students;

    @Schema(description = "Group homeroom teacher name")
    private String homeroomTeacher;

}
