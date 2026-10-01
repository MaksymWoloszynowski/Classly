package org.classly.authservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Data required to register a new account")
public record RegisterRequestDTO(
        @Schema(description = "User email address", example = "user@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank @Email String email,
        @Schema(description = "Password for the new account", example = "secret-password", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank String password,
        @Schema(description = "One-time access code assigned to the user", example = "1111", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank String accessCode) {
}