error id: file://<WORKSPACE>/backend/grade-service/src/main/java/org/edziennik/gradeservice/grade/controller/GradeController.java:org/edziennik/gradeservice/grade/dto/GradeResponseDTO#
file://<WORKSPACE>/backend/grade-service/src/main/java/org/edziennik/gradeservice/grade/controller/GradeController.java
empty definition using pc, found symbol in pc: org/edziennik/gradeservice/grade/dto/GradeResponseDTO#
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 201
uri: file://<WORKSPACE>/backend/grade-service/src/main/java/org/edziennik/gradeservice/grade/controller/GradeController.java
text:
```scala
package org.edziennik.gradeservice.grade.controller;

import jakarta.validation.groups.Default;
import org.edziennik.gradeservice.grade.dto.GradeRequestDTO;
import org.edziennik.gradeservice.grade.dto.@@GradeResponseDTO;
import org.edziennik.gradeservice.grade.dto.SubjectGradeResponseDTO;
import org.edziennik.gradeservice.grade.service.GradeService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/grade")
public class GradeController {
    private final GradeService gradeService;

    public GradeController(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    @GetMapping
    public ResponseEntity<Map<UUID, Map<UUID, SubjectGradeResponseDTO>>> getGrades(
            @RequestParam(required = false) UUID studentId,
            @RequestParam(required = false) UUID groupId,
            @RequestParam(required = false) UUID teachingAssignmentId,
            @RequestParam(required = false) UUID classificationPeriod
    ) {
        Map<UUID, Map<UUID, SubjectGradeResponseDTO>> grades = gradeService.getGrades(studentId, groupId, teachingAssignmentId, classificationPeriod);

        return ResponseEntity.ok().body(grades);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GradeResponseDTO> getGradeById(@PathVariable UUID id) {
        GradeResponseDTO gradeResponseDTO = gradeService.getGradeById(id);

        return ResponseEntity.ok().body(gradeResponseDTO);
    }

    @GetMapping("/date")
    public ResponseEntity<Map<UUID, List<GradeResponseDTO>>> getGradesByDate(
            @RequestParam UUID studentId,
            @RequestParam LocalDate from,
            @RequestParam LocalDate to
            ) {
        Map<UUID, List<GradeResponseDTO>> grades = gradeService.getGradesByDate(studentId, from, to);

        return ResponseEntity.ok().body(grades);
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

```


#### Short summary: 

empty definition using pc, found symbol in pc: org/edziennik/gradeservice/grade/dto/GradeResponseDTO#