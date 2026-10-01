package org.classly.teachingservice.assessment.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.classly.security.AuthenticatedUser;
import org.classly.teachingservice.assessment.dto.AssessmentRequestDTO;
import org.classly.teachingservice.assessment.dto.AssessmentResponseDTO;
import org.classly.teachingservice.assessment.service.AssessmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.net.URI;

@RestController
@RequestMapping("/assessment")
@Tag(name = "Assessments", description = "Manage assessments")
@SecurityRequirement(name = "cookieAuth")
public class AssessmentController {
    private final AssessmentService assessmentService;

    public AssessmentController(AssessmentService assessmentService) {
        this.assessmentService = assessmentService;
    }

    @GetMapping
        @Operation(summary = "List assessments")
        @ApiResponse(responseCode = "200", description = "Assessments returned")
    public ResponseEntity<List<AssessmentResponseDTO>> getAssessments(
            @Parameter(description = "Teaching assignment ID") @RequestParam(required = false) UUID teachingAssignmentId) {

        return ResponseEntity.ok(assessmentService.getAllAssessments(teachingAssignmentId));
    }

    @GetMapping("/student/date")
        @Operation(summary = "List assessments for a group and date range")
        @ApiResponse(responseCode = "200", description = "Assessments returned")
    public ResponseEntity<List<AssessmentResponseDTO>> getAssessmentsForStudent(
            @Parameter(description = "Group ID", required = true) @RequestParam UUID groupId,
            @Parameter(description = "Start date", example = "2026-09-01", required = true) @RequestParam LocalDate from,
            @Parameter(description = "End date", example = "2026-09-30", required = true) @RequestParam LocalDate to) {

        return ResponseEntity.ok(assessmentService.getAssessmentsForStudent(groupId, from, to));
    }

    @GetMapping("/teacher/date")
        @Operation(summary = "List assessments for the current teacher")
        @ApiResponse(responseCode = "200", description = "Assessments returned")
    public ResponseEntity<List<AssessmentResponseDTO>> getAssessmentsForTeacher(
            @AuthenticationPrincipal AuthenticatedUser user,
            @Parameter(description = "Start date", example = "2026-09-01", required = true) @RequestParam LocalDate from,
            @Parameter(description = "End date", example = "2026-09-30", required = true) @RequestParam LocalDate to) {

        return ResponseEntity.ok(assessmentService.getAssessmentsForTeacher(user, from, to));
    }

    @GetMapping("/{id}")
        @Operation(summary = "Get an assessment")
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Assessment returned", content = @Content(schema = @Schema(implementation = AssessmentResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Assessment not found", content = @Content)
        })
        public ResponseEntity<AssessmentResponseDTO> getAssessmentById(@Parameter(description = "Assessment ID", required = true) @PathVariable UUID id) {
        return ResponseEntity.ok(assessmentService.getAssessmentById(id));
    }

    @PostMapping
        @Operation(summary = "Create an assessment")
        @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Assessment created", content = @Content(schema = @Schema(implementation = AssessmentResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Validation failed", content = @Content),
            @ApiResponse(responseCode = "403", description = "User is not allowed to create assessments", content = @Content)
        })
        public ResponseEntity<AssessmentResponseDTO> createAssessment(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Assessment to create", required = true) @Valid @RequestBody AssessmentRequestDTO dto, @AuthenticationPrincipal AuthenticatedUser user) {
        AssessmentResponseDTO createdAssessment = assessmentService.createAssessment(dto, user);
        return ResponseEntity.created(URI.create("/assessment/" + createdAssessment.getId())).body(createdAssessment);
    }

    @PutMapping("/{id}")
        @Operation(summary = "Update an assessment")
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Assessment updated", content = @Content(schema = @Schema(implementation = AssessmentResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Validation failed", content = @Content),
            @ApiResponse(responseCode = "403", description = "User is not allowed to update assessments", content = @Content),
            @ApiResponse(responseCode = "404", description = "Assessment not found", content = @Content)
        })
        public ResponseEntity<AssessmentResponseDTO> updateAssessment(@Parameter(description = "Assessment ID", required = true) @PathVariable UUID id, @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated assessment data", required = true) @Valid @RequestBody AssessmentRequestDTO dto, @AuthenticationPrincipal AuthenticatedUser user) {
        return ResponseEntity.ok(assessmentService.updateAssessment(id, dto, user));
    }

    @DeleteMapping("/{id}")
        @Operation(summary = "Delete an assessment")
        @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Assessment deleted"),
            @ApiResponse(responseCode = "403", description = "User is not allowed to delete assessments", content = @Content),
            @ApiResponse(responseCode = "404", description = "Assessment not found", content = @Content)
        })
        public ResponseEntity<Void> deleteAssessment(@Parameter(description = "Assessment ID", required = true) @PathVariable UUID id, @AuthenticationPrincipal AuthenticatedUser user) {
        assessmentService.deleteAssessment(id, user);
        return ResponseEntity.noContent().build();
    }
}