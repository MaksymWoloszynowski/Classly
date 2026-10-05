package org.classly.security;

import java.util.UUID;

public record AuthenticatedUser(UUID userId, String role, UUID refId) {}