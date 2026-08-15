package org.edziennik.schoolstructureservice.group.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupRequestDTO {
    @NotBlank
    private String groupName;
}
