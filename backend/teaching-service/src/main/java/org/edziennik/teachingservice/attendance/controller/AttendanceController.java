package org.edziennik.teachingservice.attendance.controller;

import jakarta.validation.Valid;
import org.edziennik.teachingservice.attendance.dto.AttendanceBatchRequestDTO;
import org.edziennik.teachingservice.attendance.dto.AttendanceResponseDTO;
import org.edziennik.teachingservice.attendance.service.AttendanceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.edziennik.security.AuthenticatedUser;

import java.time.LocalDate;
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

    @GetMapping("/date")
    public ResponseEntity<List<AttendanceResponseDTO>> getAttendanceByStudentAndDate(
            @RequestParam(required = true) UUID studentId,
            @RequestParam(required = true) LocalDate from,
            @RequestParam(required = true) LocalDate to
    ) {
        return ResponseEntity.ok(attendanceService.getAttendanceByStudentAndDate(studentId, from, to));
    }

    @PostMapping
    public ResponseEntity<List<AttendanceResponseDTO>> createAttendanceBatch(@Valid @RequestBody AttendanceBatchRequestDTO dto, @AuthenticationPrincipal AuthenticatedUser user) {
        return ResponseEntity.ok(attendanceService.createAttendanceBatch(dto, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAttendance(@PathVariable UUID id, @AuthenticationPrincipal AuthenticatedUser user) {
        attendanceService.deleteAttendance(id, user);
        return ResponseEntity.noContent().build();
    }
}
