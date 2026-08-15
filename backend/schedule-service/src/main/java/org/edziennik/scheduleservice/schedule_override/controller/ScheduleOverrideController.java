package org.edziennik.scheduleservice.schedule_override.controller;

import jakarta.validation.Valid;
import org.edziennik.scheduleservice.schedule_override.dto.ScheduleOverrideResponseDTO;
import org.edziennik.scheduleservice.schedule_override.dto.ScheduleOverrideRequestDTO;
import org.edziennik.scheduleservice.schedule_override.service.ScheduleOverrideService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/schedule-override")
public class ScheduleOverrideController {
    private final ScheduleOverrideService scheduleOverrideService;

    public ScheduleOverrideController(ScheduleOverrideService scheduleOverrideService) {
        this.scheduleOverrideService = scheduleOverrideService;
    }

    @GetMapping
    public ResponseEntity<List<ScheduleOverrideResponseDTO>> getAllOverrides() {
        return ResponseEntity.ok(scheduleOverrideService.getAllOverrides());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleOverrideResponseDTO> getOverrideById(@PathVariable UUID id) {
        return ResponseEntity.ok(scheduleOverrideService.getOverrideById(id));
    }

    @PostMapping
    public ResponseEntity<ScheduleOverrideResponseDTO> createOverride(@Valid @RequestBody ScheduleOverrideRequestDTO dto) {
        return ResponseEntity.ok(scheduleOverrideService.createOverride(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScheduleOverrideResponseDTO> updateOverride(@PathVariable UUID id, @Valid @RequestBody ScheduleOverrideRequestDTO dto) {
        return ResponseEntity.ok(scheduleOverrideService.updateOverride(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOverride(@PathVariable UUID id) {
        scheduleOverrideService.deleteOverride(id);
        return ResponseEntity.noContent().build();
    }
}