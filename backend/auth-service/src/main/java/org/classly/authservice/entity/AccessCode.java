package org.classly.authservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "access_codes")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccessCode {
    @Id
    @UuidGenerator
    private UUID id;

    @NotBlank
    private String code;

    @NotNull
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @NotNull
    private UUID refId;

    @Builder.Default
    @NotNull
    private boolean used = false;

    @NotNull
    private Instant expiresAt;
}