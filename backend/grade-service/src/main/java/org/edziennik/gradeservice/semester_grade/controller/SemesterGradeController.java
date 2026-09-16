package org.edziennik.gradeservice.semester_grade.controller;

import jakarta.validation.groups.Default;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.edziennik.gradeservice.grade.dto.GradeResponseDTO;
import org.edziennik.gradeservice.semester_grade.dto.SemesterGradeRequestDTO;
import org.edziennik.gradeservice.semester_grade.dto.SemesterGradeResponseDTO;
import org.edziennik.gradeservice.semester_grade.service.SemesterGradeService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/semester-grade")
@Tag(name = "Semester grades", description = "Manage semester grades")
@SecurityRequirement(name = "cookieAuth")
public class SemesterGradeController {
    private final SemesterGradeService semesterGradeService;

    public SemesterGradeController(SemesterGradeService semesterGradeService) {
        this.semesterGradeService = semesterGradeService;
    }

    @GetMapping
    @Operation(summary = "List semester grades")
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Semester grades returned"),
            @ApiResponse(responseCode = "401", description = "Authentication required", content = @Content)
        })
    public ResponseEntity<List<SemesterGradeResponseDTO>> getGrades(
            @Parameter(description = "Student identifier") @RequestParam(required = false) UUID studentId,
            @Parameter(description = "Group identifier") @RequestParam(required = false) UUID groupId,
            @Parameter(description = "Subject identifier") @RequestParam(required = false) UUID subjectId,
            @Parameter(description = "Classification period identifier") @RequestParam(required = false) UUID classificationPeriod
    ) {
        List<SemesterGradeResponseDTO> grades = semesterGradeService.getGrades(studentId, groupId, subjectId, classificationPeriod);

        return ResponseEntity.ok().body(grades);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a semester grade")
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Semester grade returned", content = @Content(schema = @Schema(implementation = SemesterGradeResponseDTO.class))),
            @ApiResponse(responseCode = "401", description = "Authentication required", content = @Content),
            @ApiResponse(responseCode = "404", description = "Semester grade not found", content = @Content)
        })
        public ResponseEntity<SemesterGradeResponseDTO> getGradeById(@Parameter(description = "Semester grade identifier", required = true) @PathVariable UUID id) {
        SemesterGradeResponseDTO gradeResponseDTO = semesterGradeService.getGradeById(id);

        return ResponseEntity.ok().body(gradeResponseDTO);
    }

    @PostMapping
    @Operation(summary = "Create a semester grade")
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Semester grade created", content = @Content(schema = @Schema(implementation = SemesterGradeResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Validation failed", content = @Content),
            @ApiResponse(responseCode = "401", description = "Authentication required", content = @Content),
            @ApiResponse(responseCode = "403", description = "User is not allowed to create this grade", content = @Content)
        })
        public ResponseEntity<SemesterGradeResponseDTO> createGrade(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Semester grade to create", required = true) @Validated({Default.class}) @RequestBody SemesterGradeRequestDTO gradeRequestDTO) {
        SemesterGradeResponseDTO gradeResponseDTO = semesterGradeService.createGrade(gradeRequestDTO);

        return ResponseEntity.ok().body(gradeResponseDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a semester grade")
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Semester grade updated", content = @Content(schema = @Schema(implementation = SemesterGradeResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Validation failed", content = @Content),
            @ApiResponse(responseCode = "401", description = "Authentication required", content = @Content),
            @ApiResponse(responseCode = "403", description = "User is not allowed to update this grade", content = @Content),
            @ApiResponse(responseCode = "404", description = "Semester grade not found", content = @Content)
        })
        public ResponseEntity<SemesterGradeResponseDTO> updateGrade(@Parameter(description = "Semester grade identifier", required = true) @PathVariable UUID id, @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated semester grade data", required = true) @Validated({Default.class}) @RequestBody SemesterGradeRequestDTO gradeRequestDTO) {
        SemesterGradeResponseDTO gradeResponseDTO = semesterGradeService.updateGrade(id, gradeRequestDTO);

        return ResponseEntity.ok().body(gradeResponseDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a semester grade")
        @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Semester grade deleted"),
            @ApiResponse(responseCode = "401", description = "Authentication required", content = @Content),
            @ApiResponse(responseCode = "403", description = "User is not allowed to delete this grade", content = @Content),
            @ApiResponse(responseCode = "404", description = "Semester grade not found", content = @Content)
        })
        public ResponseEntity<Void> deleteGrade(@Parameter(description = "Semester grade identifier", required = true) @PathVariable UUID id) {
        semesterGradeService.deleteGrade(id);

        return ResponseEntity.noContent().build();
    }
}
