package org.edziennik.scheduleservice.schedule.controller;

import jakarta.validation.Valid;
import org.edziennik.scheduleservice.schedule.dto.ScheduleOccurrenceDTO;
import org.edziennik.scheduleservice.schedule.dto.ScheduleRequestDTO;
import org.edziennik.scheduleservice.schedule.dto.ScheduleResponseDTO;
import org.edziennik.scheduleservice.schedule.service.ScheduleCrudService;
import org.edziennik.scheduleservice.schedule.service.ScheduleQueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    private final ScheduleCrudService scheduleCrudService;
    private final ScheduleQueryService scheduleQueryService;

    public ScheduleController(ScheduleCrudService scheduleCrudService, ScheduleQueryService scheduleQueryService) {
        this.scheduleCrudService = scheduleCrudService;
        this.scheduleQueryService = scheduleQueryService;
    }

    @GetMapping
    public ResponseEntity<List<ScheduleResponseDTO>> getAllSchedules() {
        return ResponseEntity.ok(scheduleCrudService.getAllSchedules());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDTO> getScheduleById(@PathVariable UUID id) {
        return ResponseEntity.ok(scheduleCrudService.getScheduleById(id));
    }

    @GetMapping("/student")
    public ResponseEntity<List<ScheduleOccurrenceDTO>> getScheduleForGroup(@RequestParam(required = true) UUID groupId,
                                                                               @RequestParam(required = true) LocalDate from,
                                                                               @RequestParam(required = true) LocalDate to) {
        return ResponseEntity.ok(scheduleQueryService.getScheduleForGroup(groupId, from, to));
    }

    @GetMapping("/teacher")
    public ResponseEntity<List<ScheduleOccurrenceDTO>> getScheduleForTeacher(@RequestParam(required = true) UUID teacherId,
                                                                               @RequestParam(required = true) LocalDate from,
                                                                               @RequestParam(required = true) LocalDate to) {
        return ResponseEntity.ok(scheduleQueryService.getScheduleForTeacher(teacherId, from, to));
    }

    @PostMapping
    public ResponseEntity<ScheduleResponseDTO> createSchedule(@Valid @RequestBody ScheduleRequestDTO dto) {
        return ResponseEntity.ok(scheduleCrudService.createSchedule(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScheduleResponseDTO> updateSchedule(@PathVariable UUID id, @Valid @RequestBody ScheduleRequestDTO dto) {
        return ResponseEntity.ok(scheduleCrudService.updateSchedule(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable UUID id) {
        scheduleCrudService.deleteSchedule(id);
        return ResponseEntity.noContent().build();
    }
}