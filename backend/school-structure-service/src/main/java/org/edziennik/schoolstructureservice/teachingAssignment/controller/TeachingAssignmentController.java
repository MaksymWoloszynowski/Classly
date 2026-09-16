package org.edziennik.schoolstructureservice.teachingAssignment.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.edziennik.schoolstructureservice.teachingAssignment.dto.TeachingAssignmentRequestDTO;
import org.edziennik.schoolstructureservice.teachingAssignment.dto.TeachingAssignmentResponseDTO;
import org.edziennik.schoolstructureservice.teachingAssignment.service.TeachingAssignmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/teaching-assignment")
@Tag(name = "Teaching assignments", description = "Manage teaching assignments")
@SecurityRequirement(name = "cookieAuth")
public class TeachingAssignmentController {
    private final TeachingAssignmentService teachingAssignmentService;

    public TeachingAssignmentController(TeachingAssignmentService teachingAssignmentService) {
        this.teachingAssignmentService = teachingAssignmentService;
    }

    @GetMapping
    public ResponseEntity<List<TeachingAssignmentResponseDTO>> getAssignments(
            @RequestParam(required = false) UUID groupId,
            @RequestParam(required = false) UUID teacherId,
            @RequestParam(required = false) UUID subjectId) {

        return ResponseEntity.ok(teachingAssignmentService.getAssignments(groupId, teacherId, subjectId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeachingAssignmentResponseDTO> getAssignmentById(@PathVariable UUID id) {
        return ResponseEntity.ok(teachingAssignmentService.getAssignmentById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TeachingAssignmentResponseDTO> createAssignment(@Valid @RequestBody TeachingAssignmentRequestDTO dto) {
        return ResponseEntity.ok(teachingAssignmentService.createAssignment(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TeachingAssignmentResponseDTO> updateAssignment(@PathVariable UUID id, @Valid @RequestBody TeachingAssignmentRequestDTO dto) {
        return ResponseEntity.ok(teachingAssignmentService.updateAssignment(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteAssignment(@PathVariable UUID id) {
        teachingAssignmentService.deleteAssignment(id);
        return ResponseEntity.noContent().build();
    }
}