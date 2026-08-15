package org.edziennik.schoolstructureservice.subject.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubjectRequestDTO {
    @NotBlank
    private String name;
}
