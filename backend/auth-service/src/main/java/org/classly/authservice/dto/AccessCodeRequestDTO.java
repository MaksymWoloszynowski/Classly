package org.classly.authservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.classly.authservice.entity.UserRole;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccessCodeRequestDTO {
    @NotNull
    private UserRole role;

    @NotNull
    private UUID refId;
}
