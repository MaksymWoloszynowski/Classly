package org.edziennik.schoolstructureservice.subject.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Data required to create or update a subject")
public class SubjectRequestDTO {
    @NotBlank
    @Schema(description = "Subject name", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;
}
