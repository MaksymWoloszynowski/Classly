package org.edziennik.schoolstructureservice.teacher.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Schema(description = "Data required to create or update a teacher")
public class TeacherRequestDTO {
    @NotBlank
    @Schema(description = "Teacher first name", requiredMode = Schema.RequiredMode.REQUIRED)
    private String firstName;

    @NotBlank
    @Schema(description = "Teacher last name", requiredMode = Schema.RequiredMode.REQUIRED)
    private String lastName;
}