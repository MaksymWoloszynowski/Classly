package org.classly.scheduleservice.additional_schedule.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.classly.scheduleservice.additional_schedule.dto.AdditionalScheduleRequestDTO;
import org.classly.scheduleservice.additional_schedule.dto.AdditionalScheduleResponseDTO;
import org.classly.scheduleservice.additional_schedule.service.AdditionalScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.net.URI;

@RestController
@RequestMapping("/additional-schedule")
@Tag(name = "Additional schedules", description = "Manage one-off lessons")
@SecurityRequirement(name = "cookieAuth")
public class AdditionalScheduleController {
    private final AdditionalScheduleService additionalSessionService;

    public AdditionalScheduleController(AdditionalScheduleService additionalSessionService) {
        this.additionalSessionService = additionalSessionService;
    }

    @GetMapping
    @Operation(summary = "List additional schedules")
    @ApiResponse(responseCode = "200", description = "Additional schedules returned")
    public ResponseEntity<List<AdditionalScheduleResponseDTO>> getAllAdditionalSessions() {
        return ResponseEntity.ok(additionalSessionService.getAllAdditionalSessions());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get an additional schedule")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Additional schedule returned", content = @Content(schema = @Schema(implementation = AdditionalScheduleResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Additional schedule not found", content = @Content)
    })
    public ResponseEntity<AdditionalScheduleResponseDTO> getAdditionalSessionById(@Parameter(description = "Additional schedule ID", required = true) @PathVariable UUID id) {
        return ResponseEntity.ok(additionalSessionService.getAdditionalSessionById(id));
    }

    @PostMapping
    @Operation(summary = "Create an additional schedule")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Additional schedule created", content = @Content(schema = @Schema(implementation = AdditionalScheduleResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Validation failed", content = @Content),
        @ApiResponse(responseCode = "403", description = "User is not allowed to create additional schedules", content = @Content)
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AdditionalScheduleResponseDTO> createAdditionalSession(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Additional schedule to create", required = true) @Valid @RequestBody AdditionalScheduleRequestDTO dto) {
        AdditionalScheduleResponseDTO createdSchedule = additionalSessionService.createAdditionalSession(dto);
        return ResponseEntity.created(URI.create("/additional-schedule/" + createdSchedule.getId())).body(createdSchedule);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an additional schedule")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Additional schedule updated", content = @Content(schema = @Schema(implementation = AdditionalScheduleResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Validation failed", content = @Content),
        @ApiResponse(responseCode = "403", description = "User is not allowed to update additional schedules", content = @Content),
        @ApiResponse(responseCode = "404", description = "Additional schedule not found", content = @Content)
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AdditionalScheduleResponseDTO> updateAdditionalSession(@Parameter(description = "Additional schedule ID", required = true) @PathVariable UUID id, @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated additional schedule data", required = true) @Valid @RequestBody AdditionalScheduleRequestDTO dto) {
        return ResponseEntity.ok(additionalSessionService.updateAdditionalSession(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an additional schedule")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Additional schedule deleted"),
        @ApiResponse(responseCode = "403", description = "User is not allowed to delete additional schedules", content = @Content),
        @ApiResponse(responseCode = "404", description = "Additional schedule not found", content = @Content)
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteAdditionalSession(@Parameter(description = "Additional schedule ID", required = true) @PathVariable UUID id) {
        additionalSessionService.deleteAdditionalSession(id);
        return ResponseEntity.noContent().build();
    }
}