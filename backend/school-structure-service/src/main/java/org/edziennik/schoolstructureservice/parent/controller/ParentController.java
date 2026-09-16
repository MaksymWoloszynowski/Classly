package org.edziennik.schoolstructureservice.parent.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.edziennik.schoolstructureservice.parent.dto.ParentRequestDTO;
import org.edziennik.schoolstructureservice.parent.dto.ParentResponseDTO;
import org.edziennik.schoolstructureservice.parent.service.ParentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
    public ResponseEntity<List<ParentResponseDTO>> getAllParents() {
        return ResponseEntity.ok().body(parentService.getAllParents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParentResponseDTO> getParentById(@PathVariable UUID id) {
        return ResponseEntity.ok().body(parentService.getParentById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ParentResponseDTO> createParent(@Valid @RequestBody ParentRequestDTO parentRequestDTO) {
        return ResponseEntity.ok().body(parentService.createParent(parentRequestDTO));
    }

    @PostMapping("/{parentId}/student/{studentId}")
    public ResponseEntity<ParentResponseDTO> addStudentToParent(@PathVariable UUID parentId, @PathVariable UUID studentId) {
        return ResponseEntity.ok().body(parentService.addStudentToParent(parentId, studentId));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ParentResponseDTO> updateParent(@Valid @RequestBody ParentRequestDTO parentRequestDTO, @PathVariable UUID id) {
        return ResponseEntity.ok().body(parentService.updateParent(id, parentRequestDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteParent(@PathVariable UUID id) {
        parentService.deleteParent(id);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{parentId}/student/{studentId}")
    public ResponseEntity<ParentResponseDTO> deleteStudentFromParent(@PathVariable UUID parentId, @PathVariable UUID studentId) {
        return ResponseEntity.ok().body(parentService.deleteStudentFromParent(parentId, studentId));
    }
}
