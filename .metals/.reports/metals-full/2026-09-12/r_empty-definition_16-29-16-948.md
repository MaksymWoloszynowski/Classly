error id: file://<WORKSPACE>/backend/grade-service/src/main/java/org/edziennik/gradeservice/grade/controller/GradeController.java:org/edziennik/gradeservice/grade/service/GradeService#
file://<WORKSPACE>/backend/grade-service/src/main/java/org/edziennik/gradeservice/grade/controller/GradeController.java
empty definition using pc, found symbol in pc: org/edziennik/gradeservice/grade/service/GradeService#
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 336
uri: file://<WORKSPACE>/backend/grade-service/src/main/java/org/edziennik/gradeservice/grade/controller/GradeController.java
text:
```scala
package org.edziennik.gradeservice.grade.controller;

import jakarta.validation.groups.Default;
import org.edziennik.gradeservice.grade.dto.GradeRequestDTO;
import org.edziennik.gradeservice.grade.dto.GradeResponseDTO;
import org.edziennik.gradeservice.grade.dto.SubjectGradeResponseDTO;
import org.edziennik.gradeservice.grade.service.@@GradeService;
import org.edziennik.security.AuthenticatedUser;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public ResponseEntity<GradeResponseDTO> createGrade(@Validated({Default.class}) @RequestBody GradeRequestDTO gradeRequestDTO, @AuthenticationPrincipal AuthenticatedUser user) {
        GradeResponseDTO gradeResponseDTO = gradeService.createGrade(gradeRequestDTO, user);

        return ResponseEntity.ok().body(gradeResponseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GradeResponseDTO> updateGrade(@PathVariable UUID id, @Validated({Default.class}) @RequestBody GradeRequestDTO gradeRequestDTO, @AuthenticationPrincipal AuthenticatedUser user) {
        GradeResponseDTO gradeResponseDTO = gradeService.updateGrade(id, gradeRequestDTO, user);

        return ResponseEntity.ok().body(gradeResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGrade(@PathVariable UUID id, @AuthenticationPrincipal AuthenticatedUser user) {
        gradeService.deleteGrade(id, user);

        return ResponseEntity.noContent().build();
    }
}

```


#### Short summary: 

empty definition using pc, found symbol in pc: org/edziennik/gradeservice/grade/service/GradeService#