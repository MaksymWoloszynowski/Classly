package org.edziennik.authservice.entity;

import java.util.UUID;

public record TokenClaims(UUID userId, String role, UUID refId) {}