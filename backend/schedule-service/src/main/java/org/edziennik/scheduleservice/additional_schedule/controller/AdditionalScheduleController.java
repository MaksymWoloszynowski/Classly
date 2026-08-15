package org.edziennik.scheduleservice.additional_schedule.controller;

import jakarta.validation.Valid;
import org.edziennik.scheduleservice.additional_schedule.dto.AdditionalScheduleRequestDTO;
import org.edziennik.scheduleservice.additional_schedule.dto.AdditionalScheduleResponseDTO;
import org.edziennik.scheduleservice.additional_schedule.service.AdditionalScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/additional-schedule")
public class AdditionalScheduleController {
    private final AdditionalScheduleService additionalSessionService;

    public AdditionalScheduleController(AdditionalScheduleService additionalSessionService) {
        this.additionalSessionService = additionalSessionService;
    }

    @GetMapping
    public ResponseEntity<List<AdditionalScheduleResponseDTO>> getAllAdditionalSessions() {
        return ResponseEntity.ok(additionalSessionService.getAllAdditionalSessions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdditionalScheduleResponseDTO> getAdditionalSessionById(@PathVariable UUID id) {
        return ResponseEntity.ok(additionalSessionService.getAdditionalSessionById(id));
    }

    @PostMapping
    public ResponseEntity<AdditionalScheduleResponseDTO> createAdditionalSession(@Valid @RequestBody AdditionalScheduleRequestDTO dto) {
        return ResponseEntity.ok(additionalSessionService.createAdditionalSession(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdditionalScheduleResponseDTO> updateAdditionalSession(@PathVariable UUID id, @Valid @RequestBody AdditionalScheduleRequestDTO dto) {
        return ResponseEntity.ok(additionalSessionService.updateAdditionalSession(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdditionalSession(@PathVariable UUID id) {
        additionalSessionService.deleteAdditionalSession(id);
        return ResponseEntity.noContent().build();
    }
}