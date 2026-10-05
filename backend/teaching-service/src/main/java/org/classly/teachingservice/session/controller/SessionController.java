package org.classly.teachingservice.session.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.classly.teachingservice.session.dto.SessionRequestDTO;
import org.classly.teachingservice.session.dto.SessionResponseDTO;
import org.classly.teachingservice.session.service.SessionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.classly.security.AuthenticatedUser;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.net.URI;

@RestController
@RequestMapping("/session")
@Tag(name = "Sessions", description = "Manage concrete teaching sessions")
@SecurityRequirement(name = "cookieAuth")
public class SessionController {
    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping
        @Operation(summary = "List sessions")
        @ApiResponse(responseCode = "200", description = "Sessions returned")
    public ResponseEntity<List<SessionResponseDTO>> getSessions(
            @Parameter(description = "Teaching assignment ID") @RequestParam(required = false) UUID teachingAssignmentId,
            @Parameter(description = "Start date", example = "2026-09-01") @RequestParam(required = false) LocalDate from,
            @Parameter(description = "End date", example = "2026-09-30") @RequestParam(required = false) LocalDate to) {

        return ResponseEntity.ok(sessionService.getSessions(teachingAssignmentId, from, to));
    }

    @GetMapping("/{id}")
        @Operation(summary = "Get a session")
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Session returned", content = @Content(schema = @Schema(implementation = SessionResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Session not found", content = @Content)
        })
        public ResponseEntity<SessionResponseDTO> getSessionById(@Parameter(description = "Session ID", required = true) @PathVariable UUID id) {
        return ResponseEntity.ok(sessionService.getSessionById(id));
    }

    @GetMapping("/date")
        @Operation(summary = "List sessions for a group and date range")
        @ApiResponse(responseCode = "200", description = "Sessions returned")
    public ResponseEntity<List<SessionResponseDTO>> getSessionsByGroupAndDate(
            @Parameter(description = "Group ID", required = true) @RequestParam UUID groupId,
            @Parameter(description = "Start date", example = "2026-09-01", required = true) @RequestParam LocalDate from,
            @Parameter(description = "End date", example = "2026-09-30", required = true) @RequestParam LocalDate to
            ) {

        return ResponseEntity.ok(sessionService.getSessionsByGroupAndDate(groupId, from, to));
    }

    @PostMapping
    @Operation(summary = "Create a session")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Session created", content = @Content(schema = @Schema(implementation = SessionResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Validation failed", content = @Content),
        @ApiResponse(responseCode = "403", description = "User is not allowed to create sessions", content = @Content)
    })
    public ResponseEntity<SessionResponseDTO> createSession(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Session to create", required = true) @Valid @RequestBody SessionRequestDTO dto, @AuthenticationPrincipal AuthenticatedUser user) {
        SessionResponseDTO createdSession = sessionService.createSession(dto, user);
        return ResponseEntity.created(URI.create("/session/" + createdSession.getId())).body(createdSession);
    }

    @GetMapping("/query/teacher")
    @Operation(summary = "List sessions for the current teacher")
    @ApiResponse(responseCode = "200", description = "Sessions returned")
    public ResponseEntity<List<SessionResponseDTO>> getSessionsForTeacher(
            @AuthenticationPrincipal AuthenticatedUser user,
            @Parameter(description = "Start date", example = "2026-09-01", required = true) @RequestParam LocalDate from,
            @Parameter(description = "End date", example = "2026-09-30", required = true) @RequestParam LocalDate to) {
        return ResponseEntity.ok(sessionService.getSessionsForTeacher(user, from, to));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a session")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Session updated", content = @Content(schema = @Schema(implementation = SessionResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Validation failed", content = @Content),
        @ApiResponse(responseCode = "403", description = "User is not allowed to update sessions", content = @Content),
        @ApiResponse(responseCode = "404", description = "Session not found", content = @Content)
    })
    public ResponseEntity<SessionResponseDTO> updateSession(@Parameter(description = "Session ID", required = true) @PathVariable UUID id, @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated session data", required = true) @Valid @RequestBody SessionRequestDTO dto, @AuthenticationPrincipal AuthenticatedUser user) {
        return ResponseEntity.ok(sessionService.updateSession(id, dto, user));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a session")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Session deleted"),
        @ApiResponse(responseCode = "403", description = "User is not allowed to delete sessions", content = @Content),
        @ApiResponse(responseCode = "404", description = "Session not found", content = @Content)
    })
    public ResponseEntity<Void> deleteSession(@Parameter(description = "Session ID", required = true) @PathVariable UUID id, @AuthenticationPrincipal AuthenticatedUser user) {
        sessionService.deleteSession(id, user);
        return ResponseEntity.noContent().build();
    }
}
