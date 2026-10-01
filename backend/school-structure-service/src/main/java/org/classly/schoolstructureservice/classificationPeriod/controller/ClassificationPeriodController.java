package org.classly.schoolstructureservice.classificationPeriod.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.classly.schoolstructureservice.classificationPeriod.dto.ClassificationPeriodRequestDTO;
import org.classly.schoolstructureservice.classificationPeriod.dto.ClassificationPeriodResponseDTO;
import org.classly.schoolstructureservice.classificationPeriod.service.ClassificationPeriodService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.net.URI;

@RestController
@RequestMapping("/classification-period")
@Tag(name = "Classification periods", description = "Manage classification periods")
@SecurityRequirement(name = "cookieAuth")
public class ClassificationPeriodController {
    private final ClassificationPeriodService classificationPeriodService;

    public ClassificationPeriodController(ClassificationPeriodService classificationPeriodService) {
        this.classificationPeriodService = classificationPeriodService;
    }

    @GetMapping
    @Operation(summary = "List classification periods", description = "Returns classification periods.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Classification periods returned"),
            @ApiResponse(responseCode = "401", description = "Authentication required", content = @Content),
            @ApiResponse(responseCode = "403", description = "User is not allowed to view these classification periods", content = @Content)
    })
    public ResponseEntity<List<ClassificationPeriodResponseDTO>> getAllClassificationPeriods() {
        return ResponseEntity.ok().body(classificationPeriodService.getAllClassificationPeriods());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a classification period")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Classification period returned", content = @Content(schema = @Schema(implementation = ClassificationPeriodResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Classification period not found", content = @Content)
    })
    public ResponseEntity<ClassificationPeriodResponseDTO> getClassificationPeriodById(@Parameter(description = "Classification period ID", required = true) @PathVariable UUID id) {
        return ResponseEntity.ok().body(classificationPeriodService.getClassificationPeriodById(id));
    }

    @PostMapping
    @Operation(summary = "Create a classification period")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Classification period created", content = @Content(schema = @Schema(implementation = ClassificationPeriodResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Validation failed", content = @Content),
        @ApiResponse(responseCode = "403", description = "Admin role required", content = @Content)
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ClassificationPeriodResponseDTO> createClassificationPeriod(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Classification period to create", required = true) @Valid @RequestBody ClassificationPeriodRequestDTO classificationPeriodRequestDTO) {
        ClassificationPeriodResponseDTO createdPeriod = classificationPeriodService.createClassificationPeriod(classificationPeriodRequestDTO);
        return ResponseEntity.created(URI.create("/classification-period/" + createdPeriod.getId())).body(createdPeriod);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a classification period")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Classification period updated", content = @Content(schema = @Schema(implementation = ClassificationPeriodResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Validation failed", content = @Content),
        @ApiResponse(responseCode = "403", description = "Admin role required", content = @Content),
        @ApiResponse(responseCode = "404", description = "Classification period not found", content = @Content)
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ClassificationPeriodResponseDTO> updateClassificationPeriod(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated classification period data", required = true) @Valid @RequestBody ClassificationPeriodRequestDTO classificationPeriodRequestDTO, @Parameter(description = "Classification period ID", required = true) @PathVariable UUID id) {
        return ResponseEntity.ok().body(classificationPeriodService.updateClassificationPeriod(id, classificationPeriodRequestDTO));
    }

    @DeleteMapping("/{id}")
        @Operation(summary = "Delete a classification period")
        @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Classification period deleted"),
            @ApiResponse(responseCode = "403", description = "Admin role required", content = @Content),
            @ApiResponse(responseCode = "404", description = "Classification period not found", content = @Content)
        })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteClassificationPeriod(@Parameter(description = "Classification period ID", required = true) @PathVariable UUID id) {
        classificationPeriodService.deleteClassificationPeriod(id);

        return ResponseEntity.noContent().build();
    }
}
