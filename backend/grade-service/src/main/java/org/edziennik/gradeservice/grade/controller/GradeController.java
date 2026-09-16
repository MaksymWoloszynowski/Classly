package org.edziennik.gradeservice.grade.controller;

import jakarta.validation.groups.Default;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.edziennik.gradeservice.grade.dto.GradeRequestDTO;
import org.edziennik.gradeservice.grade.dto.GradeResponseDTO;
import org.edziennik.gradeservice.grade.dto.SubjectGradeResponseDTO;
import org.edziennik.gradeservice.grade.service.GradeService;
import org.edziennik.security.AuthenticatedUser;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/grade")
@Tag(name = "Grades", description = "Manage individual grades")
@SecurityRequirement(name = "cookieAuth")
public class GradeController {
    private final GradeService gradeService;

    public GradeController(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    @GetMapping
    @Operation(summary = "List grades", description = "Returns grades filtered by student, group, teaching assignment or classification period.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Grades returned"),
        @ApiResponse(responseCode = "401", description = "Authentication required", content = @Content),
        @ApiResponse(responseCode = "403", description = "User is not allowed to view these grades", content = @Content)
    })
    public ResponseEntity<Map<UUID, Map<UUID, SubjectGradeResponseDTO>>> getGrades(
            @Parameter(description = "Student ID") @RequestParam(required = false) UUID studentId,
            @Parameter(description = "Group ID") @RequestParam(required = false) UUID groupId,
            @Parameter(description = "Teaching assignment ID") @RequestParam(required = false) UUID teachingAssignmentId,
            @Parameter(description = "Classification period ID") @RequestParam(required = false) UUID classificationPeriod
    ) {
        Map<UUID, Map<UUID, SubjectGradeResponseDTO>> grades = gradeService.getGrades(studentId, groupId, teachingAssignmentId, classificationPeriod);

        return ResponseEntity.ok().body(grades);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a grade")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Grade returned", content = @Content(schema = @Schema(implementation = GradeResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Grade not found", content = @Content),
        @ApiResponse(responseCode = "401", description = "Authentication required", content = @Content)
    })
    public ResponseEntity<GradeResponseDTO> getGradeById(@Parameter(description = "Grade ID", required = true) @PathVariable UUID id) {
        GradeResponseDTO gradeResponseDTO = gradeService.getGradeById(id);

        return ResponseEntity.ok().body(gradeResponseDTO);
    }

    @GetMapping("/date")
    @Operation(summary = "List grades by date", description = "Returns a student's grades in the requested date range.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Grades returned"),
        @ApiResponse(responseCode = "400", description = "Invalid date range or parameter", content = @Content),
        @ApiResponse(responseCode = "401", description = "Authentication required", content = @Content)
    })
    public ResponseEntity<Map<UUID, List<GradeResponseDTO>>> getGradesByDate(
            @Parameter(description = "Student ID", required = true) @RequestParam UUID studentId,
            @Parameter(description = "Start date, inclusive, in ISO-8601 format", required = true) @RequestParam LocalDate from,
            @Parameter(description = "End date, inclusive, in ISO-8601 format", required = true) @RequestParam LocalDate to
    ) {
        Map<UUID, List<GradeResponseDTO>> grades = gradeService.getGradesByDate(studentId, from, to);

        return ResponseEntity.ok().body(grades);
    }

    @PostMapping
    @Operation(summary = "Create a grade")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Grade created", content = @Content(schema = @Schema(implementation = GradeResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Validation failed", content = @Content),
        @ApiResponse(responseCode = "401", description = "Authentication required", content = @Content),
        @ApiResponse(responseCode = "403", description = "Teacher has no access to the grade category", content = @Content),
        @ApiResponse(responseCode = "404", description = "Grade category not found", content = @Content)
    })
    @PreAuthorize("@gradeSecurity.requireGradeCategoryAccess(authentication, #gradeRequestDTO.gradeCategoryId)")
    public ResponseEntity<GradeResponseDTO> createGrade(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Grade to create", required = true) @Validated({Default.class}) @RequestBody GradeRequestDTO gradeRequestDTO, @AuthenticationPrincipal AuthenticatedUser user) {
        GradeResponseDTO gradeResponseDTO = gradeService.createGrade(gradeRequestDTO, user);

        return ResponseEntity.ok().body(gradeResponseDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a grade")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Grade updated", content = @Content(schema = @Schema(implementation = GradeResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Validation failed", content = @Content),
        @ApiResponse(responseCode = "401", description = "Authentication required", content = @Content),
        @ApiResponse(responseCode = "403", description = "Teacher has no access to this grade", content = @Content),
        @ApiResponse(responseCode = "404", description = "Grade not found", content = @Content)
    })
    @PreAuthorize("@gradeSecurity.canEdit(authentication, #id)")
    public ResponseEntity<GradeResponseDTO> updateGrade(@Parameter(description = "Grade ID", required = true) @PathVariable UUID id, @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated grade data", required = true) @Validated({Default.class}) @RequestBody GradeRequestDTO gradeRequestDTO, @AuthenticationPrincipal AuthenticatedUser user) {
        GradeResponseDTO gradeResponseDTO = gradeService.updateGrade(id, gradeRequestDTO, user);

        return ResponseEntity.ok().body(gradeResponseDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a grade")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Grade deleted"),
        @ApiResponse(responseCode = "401", description = "Authentication required", content = @Content),
        @ApiResponse(responseCode = "403", description = "Teacher has no access to this grade", content = @Content),
        @ApiResponse(responseCode = "404", description = "Grade not found", content = @Content)
    })
    @PreAuthorize("@gradeSecurity.canEdit(authentication, #id)")
    public ResponseEntity<Void> deleteGrade(@Parameter(description = "Grade ID", required = true) @PathVariable UUID id, @AuthenticationPrincipal AuthenticatedUser user) {
        gradeService.deleteGrade(id, user);

        return ResponseEntity.noContent().build();
    }
}
