error id: file://<WORKSPACE>/backend/grade-service/src/main/java/org/edziennik/gradeservice/gradeCategory/controller/GradeCategoryController.java:org/edziennik/security/AuthenticatedUser#
file://<WORKSPACE>/backend/grade-service/src/main/java/org/edziennik/gradeservice/gradeCategory/controller/GradeCategoryController.java
empty definition using pc, found symbol in pc: org/edziennik/security/AuthenticatedUser#
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 367
uri: file://<WORKSPACE>/backend/grade-service/src/main/java/org/edziennik/gradeservice/gradeCategory/controller/GradeCategoryController.java
text:
```scala
package org.edziennik.gradeservice.gradeCategory.controller;

import jakarta.validation.groups.Default;
import org.edziennik.gradeservice.gradeCategory.dto.GradeCategoryRequestDTO;
import org.edziennik.gradeservice.gradeCategory.dto.GradeCategoryResponseDTO;
import org.edziennik.gradeservice.gradeCategory.service.GradeCategoryService;
import org.edziennik.security.@@AuthenticatedUser;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/grade-category")
public class GradeCategoryController {
    private final GradeCategoryService gradeCategoryService;

    public GradeCategoryController(GradeCategoryService gradeCategoryService) {
        this.gradeCategoryService = gradeCategoryService;
    }

    @GetMapping
    public ResponseEntity<List<GradeCategoryResponseDTO>> getGradeCategories(
            @RequestParam(required = false) UUID teachingAssignmentId
    ) {
        List<GradeCategoryResponseDTO> gradeCategories = gradeCategoryService.getGradeCategories(teachingAssignmentId);

        return ResponseEntity.ok().body(gradeCategories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GradeCategoryResponseDTO> getGradeCategoryById(@PathVariable UUID id) {
        GradeCategoryResponseDTO gradeResponseDTO = gradeCategoryService.getGradeCategoryById(id);

        return ResponseEntity.ok().body(gradeResponseDTO);
    }

    @PostMapping
    public ResponseEntity<GradeCategoryResponseDTO> createGradeCategory(@Validated({Default.class}) @RequestBody GradeCategoryRequestDTO gradeRequestDTO, @AuthenticationPrincipal AuthenticatedUser user) {
        GradeCategoryResponseDTO gradeResponseDTO = gradeCategoryService.createGradeCategory(gradeRequestDTO, user);

        return ResponseEntity.ok().body(gradeResponseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GradeCategoryResponseDTO> updateGradeCategory(@PathVariable UUID id, @Validated({Default.class}) @RequestBody GradeCategoryRequestDTO gradeRequestDTO, @AuthenticationPrincipal AuthenticatedUser user) {
        GradeCategoryResponseDTO gradeResponseDTO = gradeCategoryService.updateGradeCategory(id, gradeRequestDTO, user);

        return ResponseEntity.ok().body(gradeResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGradeCategory(@PathVariable UUID id, @AuthenticationPrincipal AuthenticatedUser user) {
        gradeCategoryService.deleteGradeCategory(id, user);

        return ResponseEntity.noContent().build();
    }
}

```


#### Short summary: 

empty definition using pc, found symbol in pc: org/edziennik/security/AuthenticatedUser#