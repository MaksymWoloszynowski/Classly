package org.classly.schoolstructureservice.teacher.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.classly.schoolstructureservice.teacher.dto.TeacherRequestDTO;
import org.classly.schoolstructureservice.teacher.dto.TeacherResponseDTO;
import org.classly.schoolstructureservice.teacher.service.TeacherService;
import org.classly.schoolstructureservice.util.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.net.URI;

@RestController
@RequestMapping("/teacher")
@Tag(name = "Teachers", description = "Manage teachers")
@SecurityRequirement(name = "cookieAuth")
public class TeacherController {
    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping
    @Operation(summary = "Returns a paginated and sorted list of teachers.")
    @ApiResponse(responseCode = "200", description = "Teachers returned")
    public ResponseEntity<PageResponse<TeacherResponseDTO>> getAllTeachers(
            @Parameter(description = "Search by first name or last name")
            @RequestParam(required = false) String search,
            @PageableDefault(size = 10, sort = "lastName", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<TeacherResponseDTO> teachers = teacherService.getAllTeachers(search, pageable);

        PageResponse<TeacherResponseDTO> response = new PageResponse<>(
                teachers.getContent(),
                teachers.getNumber(),
                teachers.getSize(),
                teachers.getTotalElements(),
                teachers.getTotalPages()
        );

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a teacher")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Teacher returned", content = @Content(schema = @Schema(implementation = TeacherResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Teacher not found", content = @Content)
    })
    public ResponseEntity<TeacherResponseDTO> getTeacherById(@Parameter(description = "Teacher ID", required = true) @PathVariable UUID id) {
        return ResponseEntity.ok(teacherService.getTeacherById(id));
    }

    @PostMapping
    @Operation(summary = "Create a teacher")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Teacher created", content = @Content(schema = @Schema(implementation = TeacherResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Validation failed", content = @Content),
        @ApiResponse(responseCode = "403", description = "Admin role required", content = @Content)
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TeacherResponseDTO> createTeacher(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Teacher to create", required = true) @Valid @RequestBody TeacherRequestDTO teacherRequestDTO) {
        TeacherResponseDTO created = teacherService.createTeacher(teacherRequestDTO);
        return ResponseEntity.created(URI.create("/teacher/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a teacher")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Teacher updated", content = @Content(schema = @Schema(implementation = TeacherResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Validation failed", content = @Content),
        @ApiResponse(responseCode = "403", description = "Admin role required", content = @Content),
        @ApiResponse(responseCode = "404", description = "Teacher not found", content = @Content)
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TeacherResponseDTO> updateTeacher(@Parameter(description = "Teacher ID", required = true) @PathVariable UUID id, @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated teacher data", required = true) @Valid @RequestBody TeacherRequestDTO teacherRequestDTO) {
        TeacherResponseDTO updated = teacherService.updateTeacher(id, teacherRequestDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a teacher")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Teacher deleted"),
        @ApiResponse(responseCode = "403", description = "Admin role required", content = @Content),
        @ApiResponse(responseCode = "404", description = "Teacher not found", content = @Content)
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteTeacher(@Parameter(description = "Teacher ID", required = true) @PathVariable UUID id) {
        teacherService.deleteTeacher(id);
        return ResponseEntity.noContent().build();
    }
}