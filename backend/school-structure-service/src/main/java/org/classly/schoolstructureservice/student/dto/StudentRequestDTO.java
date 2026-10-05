package org.classly.schoolstructureservice.student.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Schema(description = "Data required to create or update a student")
public class StudentRequestDTO {
    @NotBlank
    @Schema(description = "Student social ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private String socialId;

    @NotBlank
    @Schema(description = "Student first name", requiredMode = Schema.RequiredMode.REQUIRED)
    private String firstName;

    @NotBlank
    @Schema(description = "Student last name", requiredMode = Schema.RequiredMode.REQUIRED)
    private String lastName;

    @NotNull
    @Schema(description = "Student date of birth", example = "2008-10-10", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDate dateOfBirth;

    @Schema(description = "Student group ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private UUID groupId;
}
