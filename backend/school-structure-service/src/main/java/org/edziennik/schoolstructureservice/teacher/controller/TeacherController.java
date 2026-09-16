package org.edziennik.schoolstructureservice.teacher.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.edziennik.schoolstructureservice.teacher.dto.TeacherRequestDTO;
import org.edziennik.schoolstructureservice.teacher.dto.TeacherResponseDTO;
import org.edziennik.schoolstructureservice.teacher.service.TeacherService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
    public ResponseEntity<List<TeacherResponseDTO>> getAllTeachers() {
        return ResponseEntity.ok(teacherService.getAllTeachers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherResponseDTO> getTeacherById(@PathVariable UUID id) {
        return ResponseEntity.ok(teacherService.getTeacherById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TeacherResponseDTO> createTeacher(@Valid @RequestBody TeacherRequestDTO teacherRequestDTO) {
        TeacherResponseDTO created = teacherService.createTeacher(teacherRequestDTO);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TeacherResponseDTO> updateTeacher(@PathVariable UUID id, @Valid @RequestBody TeacherRequestDTO teacherRequestDTO) {
        TeacherResponseDTO updated = teacherService.updateTeacher(id, teacherRequestDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteTeacher(@PathVariable UUID id) {
        teacherService.deleteTeacher(id);
        return ResponseEntity.noContent().build();
    }
}