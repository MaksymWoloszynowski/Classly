package org.classly.schoolstructureservice.parent.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.classly.schoolstructureservice.student.dto.StudentSummaryDTO;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Parent response returned by the service")
public class AdminParentResponseDTO {
    @Schema(description = "Parent ID")
    private UUID id;
    @Schema(description = "Parent social ID")
    private String socialID;
    @Schema(description = "Parent first name")
    private String firstName;
    @Schema(description = "Parent last name")
    private String lastName;
    @Schema(description = "Students linked to the parent")
    private Set<StudentSummaryDTO> students;
}
