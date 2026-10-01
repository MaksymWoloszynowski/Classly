package org.classly.schoolstructureservice.teachingAssignment.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.classly.schoolstructureservice.teachingAssignment.dto.TeachingAssignmentRequestDTO;
import org.classly.schoolstructureservice.teachingAssignment.dto.TeachingAssignmentResponseDTO;
import org.classly.schoolstructureservice.teachingAssignment.service.TeachingAssignmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.net.URI;

@RestController
@RequestMapping("/teaching-assignment")
@Tag(name = "Teaching assignments", description = "Manage teaching assignments")
@SecurityRequirement(name = "cookieAuth")
public class TeachingAssignmentController {
    private final TeachingAssignmentService teachingAssignmentService;

    public TeachingAssignmentController(TeachingAssignmentService teachingAssignmentService) {
        this.teachingAssignmentService = teachingAssignmentService;
    }

    @GetMapping
    @Operation(summary = "List teaching assignments")
    @ApiResponse(responseCode = "200", description = "Teaching assignments returned")
    public ResponseEntity<List<TeachingAssignmentResponseDTO>> getAssignments(
            @Parameter(description = "Group ID") @RequestParam(required = false) UUID groupId,
            @Parameter(description = "Teacher ID") @RequestParam(required = false) UUID teacherId,
            @Parameter(description = "Subject ID") @RequestParam(required = false) UUID subjectId) {

        return ResponseEntity.ok(teachingAssignmentService.getAssignments(groupId, teacherId, subjectId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a teaching assignment")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Teaching assignment returned", content = @Content(schema = @Schema(implementation = TeachingAssignmentResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Teaching assignment not found", content = @Content)
    })
    public ResponseEntity<TeachingAssignmentResponseDTO> getAssignmentById(@Parameter(description = "Teaching assignment ID", required = true) @PathVariable UUID id) {
        return ResponseEntity.ok(teachingAssignmentService.getAssignmentById(id));
    }

    @PostMapping
    @Operation(summary = "Create a teaching assignment")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Teaching assignment created", content = @Content(schema = @Schema(implementation = TeachingAssignmentResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Validation failed", content = @Content),
        @ApiResponse(responseCode = "403", description = "Admin role required", content = @Content)
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TeachingAssignmentResponseDTO> createAssignment(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Teaching assignment to create", required = true) @Valid @RequestBody TeachingAssignmentRequestDTO dto) {
        TeachingAssignmentResponseDTO createdAssignment = teachingAssignmentService.createAssignment(dto);
        return ResponseEntity.created(URI.create("/teaching-assignment/" + createdAssignment.getId())).body(createdAssignment);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a teaching assignment")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Teaching assignment updated", content = @Content(schema = @Schema(implementation = TeachingAssignmentResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Validation failed", content = @Content),
        @ApiResponse(responseCode = "403", description = "Admin role required", content = @Content),
        @ApiResponse(responseCode = "404", description = "Teaching assignment not found", content = @Content)
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TeachingAssignmentResponseDTO> updateAssignment(@Parameter(description = "Teaching assignment ID", required = true) @PathVariable UUID id, @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated teaching assignment data", required = true) @Valid @RequestBody TeachingAssignmentRequestDTO dto) {
        return ResponseEntity.ok(teachingAssignmentService.updateAssignment(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a teaching assignment")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Teaching assignment deleted"),
        @ApiResponse(responseCode = "403", description = "Admin role required", content = @Content),
        @ApiResponse(responseCode = "404", description = "Teaching assignment not found", content = @Content)
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteAssignment(@Parameter(description = "Teaching assignment ID", required = true) @PathVariable UUID id) {
        teachingAssignmentService.deleteAssignment(id);
        return ResponseEntity.noContent().build();
    }
}