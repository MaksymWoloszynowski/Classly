package org.edziennik.gradeservice.gradeCategory.controller;

import jakarta.validation.groups.Default;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.edziennik.gradeservice.gradeCategory.dto.GradeCategoryRequestDTO;
import org.edziennik.gradeservice.gradeCategory.dto.GradeCategoryResponseDTO;
import org.edziennik.gradeservice.gradeCategory.service.GradeCategoryService;
import org.edziennik.security.AuthenticatedUser;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/grade-category")
@Tag(name = "Grade categories", description = "Manage grade categories")
@SecurityRequirement(name = "cookieAuth")
public class GradeCategoryController {
    private final GradeCategoryService gradeCategoryService;

    public GradeCategoryController(GradeCategoryService gradeCategoryService) {
        this.gradeCategoryService = gradeCategoryService;
    }

    @GetMapping
    @Operation(summary = "List grade categories")
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Grade categories returned"),
            @ApiResponse(responseCode = "401", description = "Authentication required", content = @Content)
        })
    public ResponseEntity<List<GradeCategoryResponseDTO>> getGradeCategories(
            @Parameter(description = "Teaching assignment identifier") @RequestParam(required = false) UUID teachingAssignmentId,
            @Parameter(description = "Classification period identifier") @RequestParam(required = false) UUID classificationPeriod
    ) {
        List<GradeCategoryResponseDTO> gradeCategories = gradeCategoryService.getGradeCategories(teachingAssignmentId, classificationPeriod);

        return ResponseEntity.ok().body(gradeCategories);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a grade category")
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Grade category returned", content = @Content(schema = @Schema(implementation = GradeCategoryResponseDTO.class))),
            @ApiResponse(responseCode = "401", description = "Authentication required", content = @Content),
            @ApiResponse(responseCode = "404", description = "Grade category not found", content = @Content)
        })
        public ResponseEntity<GradeCategoryResponseDTO> getGradeCategoryById(@Parameter(description = "Grade category identifier", required = true) @PathVariable UUID id) {
        GradeCategoryResponseDTO gradeResponseDTO = gradeCategoryService.getGradeCategoryById(id);

        return ResponseEntity.ok().body(gradeResponseDTO);
    }

    @PostMapping
    @Operation(summary = "Create a grade category")
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Grade category created", content = @Content(schema = @Schema(implementation = GradeCategoryResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Validation failed", content = @Content),
            @ApiResponse(responseCode = "401", description = "Authentication required", content = @Content),
            @ApiResponse(responseCode = "403", description = "Teacher has no access to the teaching assignment", content = @Content)
        })
        public ResponseEntity<GradeCategoryResponseDTO> createGradeCategory(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Grade category to create", required = true) @Validated({Default.class}) @RequestBody GradeCategoryRequestDTO gradeRequestDTO, @AuthenticationPrincipal AuthenticatedUser user) {
        GradeCategoryResponseDTO gradeResponseDTO = gradeCategoryService.createGradeCategory(gradeRequestDTO, user);

        return ResponseEntity.ok().body(gradeResponseDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a grade category")
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Grade category updated", content = @Content(schema = @Schema(implementation = GradeCategoryResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Validation failed", content = @Content),
            @ApiResponse(responseCode = "401", description = "Authentication required", content = @Content),
            @ApiResponse(responseCode = "403", description = "Teacher has no access to the teaching assignment", content = @Content),
            @ApiResponse(responseCode = "404", description = "Grade category not found", content = @Content)
        })
        public ResponseEntity<GradeCategoryResponseDTO> updateGradeCategory(@Parameter(description = "Grade category identifier", required = true) @PathVariable UUID id, @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated grade category data", required = true) @Validated({Default.class}) @RequestBody GradeCategoryRequestDTO gradeRequestDTO, @AuthenticationPrincipal AuthenticatedUser user) {
        GradeCategoryResponseDTO gradeResponseDTO = gradeCategoryService.updateGradeCategory(id, gradeRequestDTO, user);

        return ResponseEntity.ok().body(gradeResponseDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a grade category")
        @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Grade category deleted"),
            @ApiResponse(responseCode = "401", description = "Authentication required", content = @Content),
            @ApiResponse(responseCode = "403", description = "Teacher has no access to the teaching assignment", content = @Content),
            @ApiResponse(responseCode = "404", description = "Grade category not found", content = @Content)
        })
        public ResponseEntity<Void> deleteGradeCategory(@Parameter(description = "Grade category identifier", required = true) @PathVariable UUID id, @AuthenticationPrincipal AuthenticatedUser user) {
        gradeCategoryService.deleteGradeCategory(id, user);

        return ResponseEntity.noContent().build();
    }
}
