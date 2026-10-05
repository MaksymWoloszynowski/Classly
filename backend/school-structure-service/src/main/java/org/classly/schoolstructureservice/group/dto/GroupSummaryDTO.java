package org.classly.schoolstructureservice.group.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Group summary used in teacher response")
public class GroupSummaryDTO {
    @Schema(description = "Group ID")
    private UUID id;

    @Schema(description = "Group name")
    private String name;
}
