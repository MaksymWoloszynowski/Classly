package org.edziennik.gradeservice.grade.controller;

import jakarta.validation.groups.Default;
import org.edziennik.gradeservice.grade.dto.GradeRequestDTO;
import org.edziennik.gradeservice.grade.dto.GradeResponseDTO;
import org.edziennik.gradeservice.grade.service.GradeService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/grade")
public class GradeController {
    private final GradeService gradeService;

    public GradeController(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    @GetMapping
    public ResponseEntity<List<GradeResponseDTO>> getGrades(
            @RequestParam(required = false) UUID studentId,
            @RequestParam(required = false) UUID groupId,
            @RequestParam(required = false) UUID subjectId,
            @RequestParam(required = false) UUID classificationPeriod
    ) {
        List<GradeResponseDTO> grades = gradeService.getGrades(studentId, groupId, subjectId, classificationPeriod);

        return ResponseEntity.ok().body(grades);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GradeResponseDTO> getGradeById(@PathVariable UUID id) {
        GradeResponseDTO gradeResponseDTO = gradeService.getGradeById(id);

        return ResponseEntity.ok().body(gradeResponseDTO);
    }

    @PostMapping
    public ResponseEntity<GradeResponseDTO> createGrade(@Validated({Default.class}) @RequestBody GradeRequestDTO gradeRequestDTO) {
        GradeResponseDTO gradeResponseDTO = gradeService.createGrade(gradeRequestDTO);

        return ResponseEntity.ok().body(gradeResponseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GradeResponseDTO> updateGrade(@PathVariable UUID id, @Validated({Default.class}) @RequestBody GradeRequestDTO gradeRequestDTO) {
        GradeResponseDTO gradeResponseDTO = gradeService.updateGrade(id, gradeRequestDTO);

        return ResponseEntity.ok().body(gradeResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGrade(@PathVariable UUID id) {
        gradeService.deleteGrade(id);

        return ResponseEntity.noContent().build();
    }
}
