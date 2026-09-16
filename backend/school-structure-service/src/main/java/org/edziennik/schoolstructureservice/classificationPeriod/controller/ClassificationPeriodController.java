package org.edziennik.schoolstructureservice.classificationPeriod.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.edziennik.schoolstructureservice.classificationPeriod.dto.ClassificationPeriodRequestDTO;
import org.edziennik.schoolstructureservice.classificationPeriod.dto.ClassificationPeriodResponseDTO;
import org.edziennik.schoolstructureservice.classificationPeriod.service.ClassificationPeriodService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
    public ResponseEntity<ClassificationPeriodResponseDTO> getClassificationPeriodById(@PathVariable UUID id) {
        return ResponseEntity.ok().body(classificationPeriodService.getClassificationPeriodById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ClassificationPeriodResponseDTO> createClassificationPeriod(@Valid @RequestBody ClassificationPeriodRequestDTO classificationPeriodRequestDTO) {
        return ResponseEntity.ok().body(classificationPeriodService.createClassificationPeriod(classificationPeriodRequestDTO));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ClassificationPeriodResponseDTO> updateClassificationPeriod(@Valid @RequestBody ClassificationPeriodRequestDTO classificationPeriodRequestDTO, @PathVariable UUID id) {
        return ResponseEntity.ok().body(classificationPeriodService.updateClassificationPeriod(id, classificationPeriodRequestDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteClassificationPeriod(@PathVariable UUID id) {
        classificationPeriodService.deleteClassificationPeriod(id);

        return ResponseEntity.noContent().build();
    }
}
