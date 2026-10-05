package org.classly.apigateway.entity;

import java.util.UUID;

public record AuthenticatedUser(UUID userId, UUID refId) {}