package org.classly.schoolstructureservice.subject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.groups.Default;
import org.classly.schoolstructureservice.subject.dto.SubjectRequestDTO;
import org.classly.schoolstructureservice.subject.dto.SubjectResponseDTO;
import org.classly.schoolstructureservice.subject.service.SubjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.net.URI;

@RestController
@RequestMapping("/subject")
@Tag(name = "Subjects", description = "Manage subjects")
@SecurityRequirement(name = "cookieAuth")
public class SubjectController {
    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping
    @Operation(summary = "List subjects")
    @ApiResponse(responseCode = "200", description = "Subjects returned")
    public ResponseEntity<List<SubjectResponseDTO>> getAllSubjects() {
        List<SubjectResponseDTO> subjects = subjectService.getAllSubjects();

        return ResponseEntity.ok().body(subjects);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a subject")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Subject returned", content = @Content(schema = @Schema(implementation = SubjectResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Subject not found", content = @Content)
    })
    public ResponseEntity<SubjectResponseDTO> getSubject(@Parameter(description = "Subject ID", required = true) @PathVariable UUID id) {
        SubjectResponseDTO subjectResponseDTO = subjectService.getSubjectById(id);

        return ResponseEntity.ok().body(subjectResponseDTO);
    }

    @PostMapping
    @Operation(summary = "Create a subject")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Subject created", content = @Content(schema = @Schema(implementation = SubjectResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Validation failed", content = @Content),
        @ApiResponse(responseCode = "403", description = "Admin role required", content = @Content)
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SubjectResponseDTO> createSubject(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Subject to create", required = true) @Validated({Default.class}) @RequestBody SubjectRequestDTO subjectRequestDTO) {
        SubjectResponseDTO subjectResponseDTO = subjectService.createSubject(subjectRequestDTO);

        return ResponseEntity.created(URI.create("/subject/" + subjectResponseDTO.getId())).body(subjectResponseDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a subject")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Subject updated", content = @Content(schema = @Schema(implementation = SubjectResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Validation failed", content = @Content),
        @ApiResponse(responseCode = "403", description = "Admin role required", content = @Content),
        @ApiResponse(responseCode = "404", description = "Subject not found", content = @Content)
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SubjectResponseDTO> updateSubject(@Parameter(description = "Subject ID", required = true) @PathVariable UUID id, @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated subject data", required = true) @Validated({Default.class}) @RequestBody SubjectRequestDTO subjectRequestDTO) {
        SubjectResponseDTO subjectResponseDTO = subjectService.updateSubject(id, subjectRequestDTO);

        return ResponseEntity.ok().body(subjectResponseDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a subject")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Subject deleted"),
        @ApiResponse(responseCode = "403", description = "Admin role required", content = @Content),
        @ApiResponse(responseCode = "404", description = "Subject not found", content = @Content)
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteSubject(@Parameter(description = "Subject ID", required = true) @PathVariable UUID id) {
        subjectService.deleteSubject(id);

        return ResponseEntity.noContent().build();
    }

}