package org.edziennik.authservice.entity;

import jakarta.persistence.*;
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

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(nullable = false)
    private UUID refId;

    @Builder.Default
    @Column(nullable = false)
    private boolean used = false;

    private Instant expiresAt;
}