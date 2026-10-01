package org.classly.scheduleservice.schedule_override.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.classly.scheduleservice.schedule_override.dto.ScheduleOverrideResponseDTO;
import org.classly.scheduleservice.schedule_override.dto.ScheduleOverrideRequestDTO;
import org.classly.scheduleservice.schedule_override.service.ScheduleOverrideService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.net.URI;

@RestController
@RequestMapping("/schedule-override")
@Tag(name = "Schedule overrides", description = "Manage changes to recurring schedule occurrences")
@SecurityRequirement(name = "cookieAuth")
public class ScheduleOverrideController {
    private final ScheduleOverrideService scheduleOverrideService;

    public ScheduleOverrideController(ScheduleOverrideService scheduleOverrideService) {
        this.scheduleOverrideService = scheduleOverrideService;
    }

    @GetMapping
    @Operation(summary = "List schedule overrides")
    @ApiResponse(responseCode = "200", description = "Schedule overrides returned")
    public ResponseEntity<List<ScheduleOverrideResponseDTO>> getAllOverrides() {
        return ResponseEntity.ok(scheduleOverrideService.getAllOverrides());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a schedule override")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Schedule override returned", content = @Content(schema = @Schema(implementation = ScheduleOverrideResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Schedule override not found", content = @Content)
    })
    public ResponseEntity<ScheduleOverrideResponseDTO> getOverrideById(@Parameter(description = "Schedule override ID", required = true) @PathVariable UUID id) {
        return ResponseEntity.ok(scheduleOverrideService.getOverrideById(id));
    }

    @PostMapping
    @Operation(summary = "Create a schedule override")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Schedule override created", content = @Content(schema = @Schema(implementation = ScheduleOverrideResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Validation failed", content = @Content),
        @ApiResponse(responseCode = "403", description = "User is not allowed to create overrides", content = @Content)
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ScheduleOverrideResponseDTO> createOverride(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Schedule override to create", required = true) @Valid @RequestBody ScheduleOverrideRequestDTO dto) {
        ScheduleOverrideResponseDTO createdOverride = scheduleOverrideService.createOverride(dto);
        return ResponseEntity.created(URI.create("/schedule-override/" + createdOverride.getId())).body(createdOverride);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a schedule override")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Schedule override updated", content = @Content(schema = @Schema(implementation = ScheduleOverrideResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Validation failed", content = @Content),
        @ApiResponse(responseCode = "403", description = "User is not allowed to update overrides", content = @Content),
        @ApiResponse(responseCode = "404", description = "Schedule override not found", content = @Content)
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ScheduleOverrideResponseDTO> updateOverride(@Parameter(description = "Schedule override ID", required = true) @PathVariable UUID id, @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated schedule override data", required = true) @Valid @RequestBody ScheduleOverrideRequestDTO dto) {
        return ResponseEntity.ok(scheduleOverrideService.updateOverride(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a schedule override")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Schedule override deleted"),
        @ApiResponse(responseCode = "403", description = "User is not allowed to delete overrides", content = @Content),
        @ApiResponse(responseCode = "404", description = "Schedule override not found", content = @Content)
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteOverride(@Parameter(description = "Schedule override ID", required = true) @PathVariable UUID id) {
        scheduleOverrideService.deleteOverride(id);
        return ResponseEntity.noContent().build();
    }
}