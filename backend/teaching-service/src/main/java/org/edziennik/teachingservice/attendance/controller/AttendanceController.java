package org.edziennik.teachingservice.attendance.controller;

import jakarta.validation.Valid;
import org.edziennik.teachingservice.attendance.dto.AttendanceBatchRequestDTO;
import org.edziennik.teachingservice.attendance.dto.AttendanceResponseDTO;
import org.edziennik.teachingservice.attendance.service.AttendanceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {
    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @GetMapping
    public ResponseEntity<List<AttendanceResponseDTO>> getAttendance(
            @RequestParam(required = false) UUID sessionId,
            @RequestParam(required = false) UUID studentId) {

        return ResponseEntity.ok(attendanceService.getAttendance(sessionId, studentId));
    }

    @PostMapping
    public ResponseEntity<List<AttendanceResponseDTO>> createAttendanceBatch(@Valid @RequestBody AttendanceBatchRequestDTO dto) {
        return ResponseEntity.ok(attendanceService.createAttendanceBatch(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAttendance(@PathVariable UUID id) {
        attendanceService.deleteAttendance(id);
        return ResponseEntity.noContent().build();
    }
}