package org.classly.schoolstructureservice.student.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.classly.schoolstructureservice.parent.dto.ParentSummaryDTO;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Individual student returned by the service")
public class StudentResponseDTO {
    @Schema(description = "Student ID")
    private UUID id;

    @Schema(description = "Student first name")
    private String firstName;

    @Schema(description = "Student last name")
    private String lastName;

    @Schema(description = "Student date of birth", example = "2008-10-10")
    private LocalDate dateOfBirth;

    @Schema(description = "Student group ID")
    private UUID groupId;

    @Schema(description = "Student group name")
    private String groupName;

    @Schema(description = "Student parents")
    private Set<ParentSummaryDTO> parents;
}
