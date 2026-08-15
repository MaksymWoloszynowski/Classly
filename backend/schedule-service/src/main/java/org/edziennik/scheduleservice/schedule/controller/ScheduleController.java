package org.edziennik.scheduleservice.schedule.controller;

import jakarta.validation.Valid;
import org.edziennik.scheduleservice.schedule.dto.ScheduleOccurrenceDTO;
import org.edziennik.scheduleservice.schedule.dto.ScheduleRequestDTO;
import org.edziennik.scheduleservice.schedule.dto.ScheduleResponseDTO;
import org.edziennik.scheduleservice.schedule.service.ScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping
    public ResponseEntity<List<ScheduleResponseDTO>> getAllSchedules() {
        return ResponseEntity.ok(scheduleService.getAllSchedules());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDTO> getScheduleById(@PathVariable UUID id) {
        return ResponseEntity.ok(scheduleService.getScheduleById(id));
    }

    @GetMapping("/date")
    public ResponseEntity<List<ScheduleOccurrenceDTO>> getGroupScheduleForWeek(@RequestParam(required = true) UUID groupId,
                                                                               @RequestParam(required = true) LocalDate from,
                                                                               @RequestParam(required = true) LocalDate to) {

        return ResponseEntity.ok(scheduleService.getGroupScheduleForDate(groupId, from, to));
    }

    @PostMapping
    public ResponseEntity<ScheduleResponseDTO> createSchedule(@Valid @RequestBody ScheduleRequestDTO dto) {
        return ResponseEntity.ok(scheduleService.createSchedule(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScheduleResponseDTO> updateSchedule(@PathVariable UUID id, @Valid @RequestBody ScheduleRequestDTO dto) {
        return ResponseEntity.ok(scheduleService.updateSchedule(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable UUID id) {
        scheduleService.deleteSchedule(id);
        return ResponseEntity.noContent().build();
    }
}