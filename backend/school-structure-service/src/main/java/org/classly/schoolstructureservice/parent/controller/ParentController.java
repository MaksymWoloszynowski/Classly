package org.classly.schoolstructureservice.parent.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.classly.schoolstructureservice.parent.dto.ParentRequestDTO;
import org.classly.schoolstructureservice.parent.dto.ParentResponseDTO;
import org.classly.schoolstructureservice.parent.service.ParentService;
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
@RequestMapping("/parent")
@Tag(name = "Parents", description = "Manage parents")
@SecurityRequirement(name = "cookieAuth")
public class ParentController {
    private final ParentService parentService;

    public ParentController(ParentService parentService) {
        this.parentService = parentService;
    }

    @GetMapping
    @Operation(summary = "List parents", description = "Returns parents.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Parents returned"),
            @ApiResponse(responseCode = "401", description = "Authentication required", content = @Content),
            @ApiResponse(responseCode = "403", description = "User is not allowed to view parents", content = @Content)
    })
    public ResponseEntity<PageResponse<ParentResponseDTO>> getAllParents(
            @Parameter(description = "Search by first name or last name") @RequestParam(required = false) String search,
            @PageableDefault(size = 10, sort = "lastName", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<ParentResponseDTO> parents = parentService.getAllParents(search, pageable);
        PageResponse<ParentResponseDTO> response = new PageResponse<>(
                parents.getContent(),
                parents.getNumber(),
                parents.getSize(),
                parents.getTotalElements(),
                parents.getTotalPages()
        );
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a parent")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Parent returned", content = @Content(schema = @Schema(implementation = ParentResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Parent not found", content = @Content)
    })
    public ResponseEntity<ParentResponseDTO> getParentById(@Parameter(description = "Parent ID", required = true) @PathVariable UUID id) {
        return ResponseEntity.ok().body(parentService.getParentById(id));
    }

    @PostMapping
    @Operation(summary = "Create a parent")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Parent created", content = @Content(schema = @Schema(implementation = ParentResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Validation failed", content = @Content),
        @ApiResponse(responseCode = "403", description = "Admin role required", content = @Content)
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ParentResponseDTO> createParent(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Parent to create", required = true) @Valid @RequestBody ParentRequestDTO parentRequestDTO) {
        ParentResponseDTO createdParent = parentService.createParent(parentRequestDTO);
        return ResponseEntity.created(URI.create("/parent/" + createdParent.getId())).body(createdParent);
    }

    @PostMapping("/{parentId}/student/{studentId}")
    @Operation(summary = "Add a student to a parent")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Student added to parent", content = @Content(schema = @Schema(implementation = ParentResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Parent or student not found", content = @Content)
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ParentResponseDTO> addStudentToParent(@Parameter(description = "Parent ID", required = true) @PathVariable UUID parentId, @Parameter(description = "Student ID", required = true) @PathVariable UUID studentId) {
        return ResponseEntity.ok().body(parentService.addStudentToParent(parentId, studentId));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a parent")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Parent updated", content = @Content(schema = @Schema(implementation = ParentResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Validation failed", content = @Content),
        @ApiResponse(responseCode = "403", description = "Admin role required", content = @Content),
        @ApiResponse(responseCode = "404", description = "Parent not found", content = @Content)
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ParentResponseDTO> updateParent(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated parent data", required = true) @Valid @RequestBody ParentRequestDTO parentRequestDTO, @Parameter(description = "Parent ID", required = true) @PathVariable UUID id) {
        return ResponseEntity.ok().body(parentService.updateParent(id, parentRequestDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a parent")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Parent deleted"),
        @ApiResponse(responseCode = "403", description = "Admin role required", content = @Content),
        @ApiResponse(responseCode = "404", description = "Parent not found", content = @Content)
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteParent(@Parameter(description = "Parent ID", required = true) @PathVariable UUID id) {
        parentService.deleteParent(id);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{parentId}/student/{studentId}")
    @Operation(summary = "Remove a student from a parent")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Student removed from parent", content = @Content(schema = @Schema(implementation = ParentResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Parent or student not found", content = @Content)
    })
    public ResponseEntity<ParentResponseDTO> deleteStudentFromParent(@Parameter(description = "Parent ID", required = true) @PathVariable UUID parentId, @Parameter(description = "Student ID", required = true) @PathVariable UUID studentId) {
        return ResponseEntity.ok().body(parentService.deleteStudentFromParent(parentId, studentId));
    }
}
