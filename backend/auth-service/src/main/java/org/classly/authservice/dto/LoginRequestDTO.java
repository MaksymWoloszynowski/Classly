package org.classly.authservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Credentials used to log in")
public record LoginRequestDTO(
        @Schema(description = "User email address", example = "user@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank @Email String email,
        @Schema(description = "User password", example = "secret-password", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank String password) {
}