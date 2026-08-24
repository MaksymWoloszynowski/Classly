package org.edziennik.gradeservice.semester_grade.controller;

import jakarta.validation.groups.Default;
import org.edziennik.gradeservice.grade.dto.GradeResponseDTO;
import org.edziennik.gradeservice.semester_grade.dto.SemesterGradeRequestDTO;
import org.edziennik.gradeservice.semester_grade.dto.SemesterGradeResponseDTO;
import org.edziennik.gradeservice.semester_grade.service.SemesterGradeService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/semester-grade")
public class SemesterGradeController {
    private final SemesterGradeService semesterGradeService;

    public SemesterGradeController(SemesterGradeService semesterGradeService) {
        this.semesterGradeService = semesterGradeService;
    }

    @GetMapping
    public ResponseEntity<List<SemesterGradeResponseDTO>> getGrades(
            @RequestParam(required = false) UUID studentId,
            @RequestParam(required = false) UUID groupId,
            @RequestParam(required = false) UUID subjectId,
            @RequestParam(required = false) UUID classificationPeriod
    ) {
        List<SemesterGradeResponseDTO> grades = semesterGradeService.getGrades(studentId, groupId, subjectId, classificationPeriod);

        return ResponseEntity.ok().body(grades);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SemesterGradeResponseDTO> getGradeById(@PathVariable UUID id) {
        SemesterGradeResponseDTO gradeResponseDTO = semesterGradeService.getGradeById(id);

        return ResponseEntity.ok().body(gradeResponseDTO);
    }

    @PostMapping
    public ResponseEntity<SemesterGradeResponseDTO> createGrade(@Validated({Default.class}) @RequestBody SemesterGradeRequestDTO gradeRequestDTO) {
        SemesterGradeResponseDTO gradeResponseDTO = semesterGradeService.createGrade(gradeRequestDTO);

        return ResponseEntity.ok().body(gradeResponseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SemesterGradeResponseDTO> updateGrade(@PathVariable UUID id, @Validated({Default.class}) @RequestBody SemesterGradeRequestDTO gradeRequestDTO) {
        SemesterGradeResponseDTO gradeResponseDTO = semesterGradeService.updateGrade(id, gradeRequestDTO);

        return ResponseEntity.ok().body(gradeResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGrade(@PathVariable UUID id) {
        semesterGradeService.deleteGrade(id);

        return ResponseEntity.noContent().build();
    }
}
