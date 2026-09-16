package org.edziennik.schoolstructureservice.subject.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.groups.Default;
import org.edziennik.schoolstructureservice.subject.dto.SubjectRequestDTO;
import org.edziennik.schoolstructureservice.subject.dto.SubjectResponseDTO;
import org.edziennik.schoolstructureservice.subject.service.SubjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
    public ResponseEntity<List<SubjectResponseDTO>> getAllSubjects() {
        List<SubjectResponseDTO> subjects = subjectService.getAllSubjects();

        return ResponseEntity.ok().body(subjects);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubjectResponseDTO> getSubject(@PathVariable UUID id) {
        SubjectResponseDTO subjectResponseDTO = subjectService.getSubjectById(id);

        return ResponseEntity.ok().body(subjectResponseDTO);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SubjectResponseDTO> createSubject(@Validated({Default.class}) @RequestBody SubjectRequestDTO subjectRequestDTO) {
        SubjectResponseDTO subjectResponseDTO = subjectService.createSubject(subjectRequestDTO);

        return ResponseEntity.ok().body(subjectResponseDTO);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SubjectResponseDTO> updateSubject(@PathVariable UUID id, @Validated({Default.class}) @RequestBody SubjectRequestDTO subjectRequestDTO) {
        SubjectResponseDTO subjectResponseDTO = subjectService.updateSubject(id, subjectRequestDTO);

        return ResponseEntity.ok().body(subjectResponseDTO);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteSubject(@PathVariable UUID id) {
        subjectService.deleteSubject(id);

        return ResponseEntity.noContent().build();
    }

}