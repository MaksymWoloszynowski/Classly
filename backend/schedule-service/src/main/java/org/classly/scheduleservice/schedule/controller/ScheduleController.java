package org.classly.scheduleservice.schedule.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.classly.scheduleservice.schedule.dto.ScheduleOccurrenceDTO;
import org.classly.scheduleservice.schedule.dto.ScheduleRequestDTO;
import org.classly.scheduleservice.schedule.dto.ScheduleResponseDTO;
import org.classly.scheduleservice.schedule.service.ScheduleCrudService;
import org.classly.scheduleservice.schedule.service.ScheduleQueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.net.URI;

@RestController
@RequestMapping("/schedule")
@Tag(name = "Schedules", description = "Manage recurring schedules and resolved schedule occurrences")
@SecurityRequirement(name = "cookieAuth")
public class ScheduleController {
    private final ScheduleCrudService scheduleCrudService;
    private final ScheduleQueryService scheduleQueryService;

    public ScheduleController(ScheduleCrudService scheduleCrudService, ScheduleQueryService scheduleQueryService) {
        this.scheduleCrudService = scheduleCrudService;
        this.scheduleQueryService = scheduleQueryService;
    }

    @GetMapping
    @Operation(summary = "List schedules")
    @ApiResponse(responseCode = "200", description = "Schedules returned")
    public ResponseEntity<List<ScheduleResponseDTO>> getAllSchedules() {
        return ResponseEntity.ok(scheduleCrudService.getAllSchedules());
    }

    @GetMapping("/{id}")
        @Operation(summary = "Get a schedule")
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Schedule returned", content = @Content(schema = @Schema(implementation = ScheduleResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Schedule not found", content = @Content)
        })
        public ResponseEntity<ScheduleResponseDTO> getScheduleById(@Parameter(description = "Schedule ID", required = true) @PathVariable UUID id) {
        return ResponseEntity.ok(scheduleCrudService.getScheduleById(id));
    }

    @GetMapping("/student")
    @Operation(summary = "Get a group's schedule")
    @ApiResponse(responseCode = "200", description = "Schedule occurrences returned")
    public ResponseEntity<List<ScheduleOccurrenceDTO>> getScheduleForGroup(@Parameter(description = "Group ID", required = true) @RequestParam UUID groupId,
                                                                               @Parameter(description = "Start date", example = "2026-09-01", required = true) @RequestParam LocalDate from,
                                                                               @Parameter(description = "End date", example = "2026-09-30", required = true) @RequestParam LocalDate to) {
        return ResponseEntity.ok(scheduleQueryService.getScheduleForGroup(groupId, from, to));
    }

    @GetMapping("/teacher")
    @Operation(summary = "Get a teacher's schedule")
    @ApiResponse(responseCode = "200", description = "Schedule occurrences returned")
    public ResponseEntity<List<ScheduleOccurrenceDTO>> getScheduleForTeacher(@Parameter(description = "Teacher ID", required = true) @RequestParam UUID teacherId,
                                                                               @Parameter(description = "Start date", example = "2026-09-01", required = true) @RequestParam LocalDate from,
                                                                               @Parameter(description = "End date", example = "2026-09-30", required = true) @RequestParam LocalDate to) {
        return ResponseEntity.ok(scheduleQueryService.getScheduleForTeacher(teacherId, from, to));
    }

    @PostMapping
    @Operation(summary = "Create a schedule")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Schedule created", content = @Content(schema = @Schema(implementation = ScheduleResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Validation failed", content = @Content),
        @ApiResponse(responseCode = "403", description = "User is not allowed to create schedules", content = @Content)
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ScheduleResponseDTO> createSchedule(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Schedule to create", required = true) @Valid @RequestBody ScheduleRequestDTO dto) {
        ScheduleResponseDTO createdSchedule = scheduleCrudService.createSchedule(dto);
        return ResponseEntity.created(URI.create("/schedule/" + createdSchedule.getId())).body(createdSchedule);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a schedule")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Schedule updated", content = @Content(schema = @Schema(implementation = ScheduleResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Validation failed", content = @Content),
        @ApiResponse(responseCode = "403", description = "User is not allowed to update schedules", content = @Content),
        @ApiResponse(responseCode = "404", description = "Schedule not found", content = @Content)
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ScheduleResponseDTO> updateSchedule(@Parameter(description = "Schedule ID", required = true) @PathVariable UUID id, @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated schedule data", required = true) @Valid @RequestBody ScheduleRequestDTO dto) {
        return ResponseEntity.ok(scheduleCrudService.updateSchedule(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a schedule")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Schedule deleted"),
        @ApiResponse(responseCode = "403", description = "User is not allowed to delete schedules", content = @Content),
        @ApiResponse(responseCode = "404", description = "Schedule not found", content = @Content)
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteSchedule(@Parameter(description = "Schedule ID", required = true) @PathVariable UUID id) {
        scheduleCrudService.deleteSchedule(id);
        return ResponseEntity.noContent().build();
    }
}