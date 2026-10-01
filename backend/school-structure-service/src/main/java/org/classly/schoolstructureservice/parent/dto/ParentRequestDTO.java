package org.classly.schoolstructureservice.parent.dto;

import jakarta.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Data required to create or update a parent")
public class ParentRequestDTO {
    @NotBlank
    @Schema(description = "Parent social ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private String socialId;

    @NotBlank
    @Schema(description = "Parent first name", example = "Anna", requiredMode = Schema.RequiredMode.REQUIRED)
    private String firstName;

    @NotBlank
    @Schema(description = "Parent last name", example = "Kowalska", requiredMode = Schema.RequiredMode.REQUIRED)
    private String lastName;
}
