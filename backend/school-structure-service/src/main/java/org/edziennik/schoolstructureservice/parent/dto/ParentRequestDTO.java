package org.edziennik.schoolstructureservice.parent.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParentRequestDTO {
    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;
}
