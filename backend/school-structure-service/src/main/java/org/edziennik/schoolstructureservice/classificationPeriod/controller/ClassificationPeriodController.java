package org.edziennik.schoolstructureservice.classificationPeriod.controller;

import jakarta.validation.Valid;
import org.edziennik.schoolstructureservice.classificationPeriod.dto.ClassificationPeriodRequestDTO;
import org.edziennik.schoolstructureservice.classificationPeriod.dto.ClassificationPeriodResponseDTO;
import org.edziennik.schoolstructureservice.classificationPeriod.service.ClassificationPeriodService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/classification-period")
public class ClassificationPeriodController {
    private final ClassificationPeriodService classificationPeriodService;

    public ClassificationPeriodController(ClassificationPeriodService classificationPeriodService) {
        this.classificationPeriodService = classificationPeriodService;
    }

    @GetMapping
    public ResponseEntity<List<ClassificationPeriodResponseDTO>> getAllClassificationPeriods() {
        return ResponseEntity.ok().body(classificationPeriodService.getAllClassificationPeriods());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassificationPeriodResponseDTO> getClassificationPeriodById(@PathVariable UUID id) {
        return ResponseEntity.ok().body(classificationPeriodService.getClassificationPeriodById(id));
    }

    @PostMapping
    public ResponseEntity<ClassificationPeriodResponseDTO> createClassificationPeriod(@Valid @RequestBody ClassificationPeriodRequestDTO classificationPeriodRequestDTO) {
        return ResponseEntity.ok().body(classificationPeriodService.createClassificationPeriod(classificationPeriodRequestDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClassificationPeriodResponseDTO> updateClassificationPeriod(@Valid @RequestBody ClassificationPeriodRequestDTO classificationPeriodRequestDTO, @PathVariable UUID id) {
        return ResponseEntity.ok().body(classificationPeriodService.updateClassificationPeriod(id, classificationPeriodRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClassificationPeriod(@PathVariable UUID id) {
        classificationPeriodService.deleteClassificationPeriod(id);

        return ResponseEntity.noContent().build();
    }
}
