package org.classly.authservice.entity;

import java.util.UUID;

public record AuthenticatedUser(UUID userId, UUID refId) {}