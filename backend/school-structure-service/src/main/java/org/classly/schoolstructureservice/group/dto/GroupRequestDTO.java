package org.classly.schoolstructureservice.group.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Data required to create or update a group")
public class GroupRequestDTO {
    @NotBlank
    @Schema(description = "Group name")
    private String groupName;
}
