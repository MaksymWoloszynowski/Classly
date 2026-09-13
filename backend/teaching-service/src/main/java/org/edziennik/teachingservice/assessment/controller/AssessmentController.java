package org.edziennik.teachingservice.assessment.controller;

import jakarta.validation.Valid;
import org.edziennik.security.AuthenticatedUser;
import org.edziennik.teachingservice.assessment.dto.AssessmentRequestDTO;
import org.edziennik.teachingservice.assessment.dto.AssessmentResponseDTO;
import org.edziennik.teachingservice.assessment.service.AssessmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/assessment")
public class AssessmentController {
    private final AssessmentService assessmentService;

    public AssessmentController(AssessmentService assessmentService) {
        this.assessmentService = assessmentService;
    }

    @GetMapping
    public ResponseEntity<List<AssessmentResponseDTO>> getAssessments(
            @RequestParam(required = false) UUID teachingAssignmentId) {

        return ResponseEntity.ok(assessmentService.getAllAssessments(teachingAssignmentId));
    }

    @GetMapping("/student/date")
    public ResponseEntity<List<AssessmentResponseDTO>> getAssessmentsForStudent(
            @RequestParam(required = true) UUID groupId,
            @RequestParam(required = true) LocalDate from,
            @RequestParam(required = true) LocalDate to) {

        return ResponseEntity.ok(assessmentService.getAssessmentsForStudent(groupId, from, to));
    }

    @GetMapping("/teacher/date")
    public ResponseEntity<List<AssessmentResponseDTO>> getAssessmentsForTeacher(
            @AuthenticationPrincipal AuthenticatedUser user,
            @RequestParam(required = true) LocalDate from,
            @RequestParam(required = true) LocalDate to) {

        return ResponseEntity.ok(assessmentService.getAssessmentsForTeacher(user, from, to));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssessmentResponseDTO> getAssessmentById(@PathVariable UUID id) {
        return ResponseEntity.ok(assessmentService.getAssessmentById(id));
    }

    @PostMapping
    public ResponseEntity<AssessmentResponseDTO> createAssessment(@Valid @RequestBody AssessmentRequestDTO dto, @AuthenticationPrincipal AuthenticatedUser user) {
        return ResponseEntity.ok(assessmentService.createAssessment(dto, user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AssessmentResponseDTO> updateAssessment(@PathVariable UUID id, @Valid @RequestBody AssessmentRequestDTO dto, @AuthenticationPrincipal AuthenticatedUser user) {
        return ResponseEntity.ok(assessmentService.updateAssessment(id, dto, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssessment(@PathVariable UUID id, @AuthenticationPrincipal AuthenticatedUser user) {
        assessmentService.deleteAssessment(id, user);
        return ResponseEntity.noContent().build();
    }
}