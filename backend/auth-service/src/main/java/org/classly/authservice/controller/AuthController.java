package org.classly.authservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.classly.authservice.dto.LoginRequestDTO;
import org.classly.authservice.dto.RegisterRequestDTO;
import org.classly.authservice.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Register accounts and manage authentication tokens")
public class AuthController {
    private final AuthService authService;

    public AuthController( AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    @Operation(summary = "Log in", description = "Authenticates a user and sets access and refresh token cookies.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Login successful"),
        @ApiResponse(responseCode = "400", description = "Invalid credentials format", content = @Content),
        @ApiResponse(responseCode = "401", description = "Invalid credentials", content = @Content)
    })
    public ResponseEntity<Void> login(@Valid @RequestBody LoginRequestDTO dto, HttpServletResponse response) {
        authService.login(dto, response);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    @Operation(summary = "Register an account", description = "Creates an account using a one-time access code.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Registration successful"),
        @ApiResponse(responseCode = "400", description = "Invalid registration data", content = @Content),
        @ApiResponse(responseCode = "409", description = "Account or access code already exists", content = @Content)
    })
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterRequestDTO dto, HttpServletResponse response) {
        authService.register(dto, response);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/refresh")
    @Operation(summary = "Refresh the access token", description = "Issues a new access token using the refresh token cookie.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Token refreshed"),
        @ApiResponse(responseCode = "401", description = "Refresh token missing or invalid", content = @Content)
    })
    public ResponseEntity<Void> refresh(@CookieValue(value = "refresh_token", required = false) String refreshToken, HttpServletResponse response) {
        if (refreshToken == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        authService.refresh(refreshToken, response);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/logout")
    @Operation(summary = "Log out", description = "Revokes the refresh token and clears authentication cookies.")
    @ApiResponse(responseCode = "200", description = "Logout successful")
    public ResponseEntity<Void> logout(@CookieValue(value = "refresh_token", required = false) String refreshToken, HttpServletResponse response) {

        authService.logout(refreshToken, response);

        return ResponseEntity.ok().build();
    }
}