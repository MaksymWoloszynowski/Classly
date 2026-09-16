package org.edziennik.schoolstructureservice.student.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Student summary used in parent response")
public class StudentSummaryDTO {
    @Schema(description = "Student ID")
    private UUID id;

    @Schema(description = "Student first name")
    private String firstName;

    @Schema(description = "Student last name")
    private String lastName;
}