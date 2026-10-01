package org.classly.authservice.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.classly.authservice.entity.TokenClaims;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.UUID;

@Service
public class JwtService {

    private final SecretKey signingKey;
    private final long accessTokenValidityMs;

    public JwtService(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.access-token-validity-ms:900000}") long accessTokenValidityMs) {
        this.signingKey = Keys.hmacShaKeyFor(secret.getBytes());
        this.accessTokenValidityMs = accessTokenValidityMs;
    }

    public String generateAccessToken(TokenClaims claims) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + accessTokenValidityMs);

        var builder = Jwts.builder()
                .subject(claims.userId().toString())
                .claim("role", claims.role())
                .issuedAt(now)
                .expiration(expiry);

        if (claims.refId() != null) {
            builder.claim("refId", claims.refId().toString());
        }

        return builder.signWith(signingKey).compact();
    }

    public Claims parseAndValidate(String token) {
        return Jwts.parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public TokenClaims extractClaims(String token) {
        Claims claims = parseAndValidate(token);
        String refIdStr = claims.get("refId", String.class);

        return new TokenClaims(
                UUID.fromString(claims.getSubject()),
                claims.get("role", String.class),
                refIdStr != null ? UUID.fromString(refIdStr) : null
        );
    }

    public boolean isValid(String token) {
        try {
            parseAndValidate(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}