package org.edziennik.schoolstructureservice.parent.controller;

import jakarta.validation.Valid;
import org.edziennik.schoolstructureservice.parent.dto.ParentRequestDTO;
import org.edziennik.schoolstructureservice.parent.dto.ParentResponseDTO;
import org.edziennik.schoolstructureservice.parent.service.ParentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/parent")
public class ParentController {
    private final ParentService parentService;

    public ParentController(ParentService parentService) {
        this.parentService = parentService;
    }

    @GetMapping
    public ResponseEntity<List<ParentResponseDTO>> getAllParents() {
        return ResponseEntity.ok().body(parentService.getAllParents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParentResponseDTO> getParentById(@PathVariable UUID id) {
        return ResponseEntity.ok().body(parentService.getParentById(id));
    }

    @PostMapping
    public ResponseEntity<ParentResponseDTO> createParent(@Valid @RequestBody ParentRequestDTO parentRequestDTO) {
        return ResponseEntity.ok().body(parentService.createParent(parentRequestDTO));
    }

    @PostMapping("/{parentId}/student/{studentId}")
    public ResponseEntity<ParentResponseDTO> addStudentToParent(@PathVariable UUID parentId, @PathVariable UUID studentId) {
        return ResponseEntity.ok().body(parentService.addStudentToParent(parentId, studentId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParentResponseDTO> updateParent(@Valid @RequestBody ParentRequestDTO parentRequestDTO, @PathVariable UUID id) {
        return ResponseEntity.ok().body(parentService.updateParent(id, parentRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParent(@PathVariable UUID id) {
        parentService.deleteParent(id);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{parentId}/student/{studentId}")
    public ResponseEntity<ParentResponseDTO> deleteStudentFromParent(@PathVariable UUID parentId, @PathVariable UUID studentId) {
        return ResponseEntity.ok().body(parentService.deleteStudentFromParent(parentId, studentId));
    }
}
