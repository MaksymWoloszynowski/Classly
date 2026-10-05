package org.classly.schoolstructureservice.parent.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Parent summary")
public class ParentSummaryDTO {
    @Schema(description = "Parent ID")
    private UUID id;
    @Schema(description = "Parent first name")
    private String firstName;
    @Schema(description = "Parent last name")
    private String lastName;
}