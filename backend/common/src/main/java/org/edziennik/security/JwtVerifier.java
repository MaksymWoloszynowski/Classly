package org.edziennik.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Optional;
import java.util.UUID;

@Component
public class JwtVerifier {
    private final SecretKey signingKey;

    public JwtVerifier(@Value("${jwt.secret}") String secret) {
        this.signingKey = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public Optional<AuthenticatedUser> verify(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(signingKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            UUID userId = UUID.fromString(claims.getSubject());
            String role = claims.get("role", String.class);
            String refIdStr = claims.get("refId", String.class);
            UUID refId = refIdStr != null ? UUID.fromString(refIdStr) : null;

            return Optional.of(new AuthenticatedUser(userId, role, refId));
        } catch (JwtException | IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}