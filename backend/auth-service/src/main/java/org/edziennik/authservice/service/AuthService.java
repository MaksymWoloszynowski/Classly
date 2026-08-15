package org.edziennik.authservice.service;

import jakarta.servlet.http.HttpServletResponse;
import org.edziennik.authservice.dto.LoginRequestDTO;
import org.edziennik.authservice.dto.RegisterRequestDTO;
import org.edziennik.authservice.entity.AccessCode;
import org.edziennik.authservice.entity.TokenClaims;
import org.edziennik.authservice.entity.User;
import org.edziennik.authservice.exception.EmailAlreadyExistsException;
import org.edziennik.authservice.exception.InvalidAccessCodeException;
import org.edziennik.authservice.repository.AccessCodeRepository;
import org.edziennik.authservice.repository.UserRepository;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final PasswordEncoder passwordEncoder;
    private final AccessCodeRepository accessCodeRepository;

    public AuthService(UserRepository userRepository, AuthenticationManager authenticationManager, JwtService jwtService, RefreshTokenService refreshTokenService, PasswordEncoder passwordEncoder, AccessCodeRepository accessCodeRepository) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
        this.passwordEncoder = passwordEncoder;
        this.accessCodeRepository = accessCodeRepository;
    }

    public void login(LoginRequestDTO dto, HttpServletResponse response) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.email(), dto.password()));

        User user = userRepository.findByEmail(dto.email()).orElseThrow();

        String accessToken = jwtService.generateAccessToken(new TokenClaims(user.getId(), user.getRole().toString(), user.getRefId()));
        String refreshToken = refreshTokenService.issueToken(user.getId());

        addCookie(response, "access_token", accessToken, 15 * 60);
        addCookie(response, "refresh_token", refreshToken, 30 * 24 * 60 * 60);
    }

    public void register(RegisterRequestDTO dto, HttpServletResponse response) {
        if (userRepository.findByEmail(dto.email()).isPresent()) {
            throw new EmailAlreadyExistsException("Email already registered: " + dto.email());
        }

        AccessCode accessCode = accessCodeRepository.findByCode(dto.accessCode())
                .orElseThrow(() -> new InvalidAccessCodeException("Invalid access code"));

        if (accessCode.isUsed()) {
            throw new InvalidAccessCodeException("Access code already used");
        }
        if (accessCode.getExpiresAt() != null && accessCode.getExpiresAt().isBefore(Instant.now())) {
            throw new InvalidAccessCodeException("Access code expired");
        }

        User user = User.builder()
                .email(dto.email())
                .password(passwordEncoder.encode(dto.password()))
                .role(accessCode.getRole())
                .refId(accessCode.getRefId())
                .build();

        userRepository.save(user);

        accessCode.setUsed(true);
        accessCodeRepository.save(accessCode);

        String accessToken = jwtService.generateAccessToken(new TokenClaims(user.getId(), user.getRole().toString(), user.getRefId()));
        String refreshToken = refreshTokenService.issueToken(user.getId());

        addCookie(response, "access_token", accessToken, 15 * 60);
        addCookie(response, "refresh_token", refreshToken, 30 * 24 * 60 * 60);
    }

    public void refresh(String refreshToken, HttpServletResponse response) {
        User user = refreshTokenService.validateAndGetUser(refreshToken)
                .orElseThrow(() -> new IllegalArgumentException("Invalid or expired refresh token"));

        String newAccessToken = jwtService.generateAccessToken(new TokenClaims(user.getId(), user.getRole().toString(), user.getRefId()));
        addCookie(response, "access_token", newAccessToken, 15 * 60);
    }

    public void logout(String refreshToken, HttpServletResponse response) {
        if (refreshToken != null) {
            refreshTokenService.revoke(refreshToken);
        }

        addCookie(response, "access_token", "", 0);
        addCookie(response, "refresh_token", "", 0);
    }

    private void addCookie(HttpServletResponse response, String name, String value, int maxAgeSeconds) {
        ResponseCookie cookie = ResponseCookie.from(name, value)
                .httpOnly(true)
                .secure(false)
                .path(name.equals("refresh_token") ? "/auth" : "/")
                .maxAge(maxAgeSeconds)
                .sameSite("Strict")
                .build();

        response.addHeader("Set-Cookie", cookie.toString());
    }
}
