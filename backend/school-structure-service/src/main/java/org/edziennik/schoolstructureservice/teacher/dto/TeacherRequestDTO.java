package org.edziennik.schoolstructureservice.teacher.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeacherRequestDTO {
    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;
}
