package org.classly.authservice.mapper;

import org.classly.authservice.dto.AccessCodeRequestDTO;
import org.classly.authservice.dto.AccessCodeResponseDTO;
import org.classly.authservice.entity.AccessCode;
import org.classly.authservice.entity.UserRole;
import org.classly.events.UserCreatedEvent;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class AccessCodeMapper {
    private static final long VALIDITY_DAYS = 30;
    public static AccessCodeResponseDTO toDTO(AccessCode code) {
        return AccessCodeResponseDTO.builder()
                .id(code.getId())
                .refId(code.getRefId())
                .code(code.getCode())
                .role(code.getRole())
                .used(code.isUsed())
                .expiresAt(code.getExpiresAt())
                .build();
    }

    public static AccessCode toModel(AccessCodeRequestDTO requestDTO) {
        return AccessCode.builder()
                .role(requestDTO.getRole())
                .refId(requestDTO.getRefId())
                .expiresAt(Instant.now().plus(VALIDITY_DAYS, ChronoUnit.DAYS))
                .build();
    }

    public static AccessCodeRequestDTO toRequestFromEvent(UserCreatedEvent event) {
        return AccessCodeRequestDTO.builder()
                .refId(UUID.fromString(event.getId()))
                .role(UserRole.valueOf(event.getRole()))
                .build();
    }
}
