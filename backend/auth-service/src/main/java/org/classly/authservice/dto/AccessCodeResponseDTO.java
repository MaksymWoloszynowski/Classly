package org.classly.authservice.dto;

import lombok.*;
import org.classly.authservice.entity.UserRole;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccessCodeResponseDTO {
    private UUID id;
    private UUID refId;
    private String name;
    private String code;
    private UserRole role;
    private boolean used;
    private Instant expiresAt;
}
