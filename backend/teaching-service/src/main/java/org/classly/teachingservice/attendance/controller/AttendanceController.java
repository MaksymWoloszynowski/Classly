package org.classly.teachingservice.attendance.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.classly.teachingservice.attendance.dto.AttendanceBatchRequestDTO;
import org.classly.teachingservice.attendance.dto.AttendanceResponseDTO;
import org.classly.teachingservice.attendance.service.AttendanceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.classly.security.AuthenticatedUser;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/attendance")
@Tag(name = "Attendance", description = "Manage student attendance")
@SecurityRequirement(name = "cookieAuth")
public class AttendanceController {
    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @GetMapping
    @Operation(summary = "List attendance records")
    @ApiResponse(responseCode = "200", description = "Attendance records returned")
    public ResponseEntity<List<AttendanceResponseDTO>> getAttendance(
            @Parameter(description = "Session ID") @RequestParam(required = false) UUID sessionId,
            @Parameter(description = "Student ID") @RequestParam(required = false) UUID studentId) {

        return ResponseEntity.ok(attendanceService.getAttendance(sessionId, studentId));
    }

    @GetMapping("/date")
    @Operation(summary = "List attendance by student and date range")
    @ApiResponse(responseCode = "200", description = "Attendance records returned")
    public ResponseEntity<List<AttendanceResponseDTO>> getAttendanceByStudentAndDate(
            @Parameter(description = "Student ID", required = true) @RequestParam UUID studentId,
            @Parameter(description = "Start date", example = "2026-09-01", required = true) @RequestParam LocalDate from,
            @Parameter(description = "End date", example = "2026-09-30", required = true) @RequestParam LocalDate to
    ) {
        return ResponseEntity.ok(attendanceService.getAttendanceByStudentAndDate(studentId, from, to));
    }

    @PostMapping
    @Operation(summary = "Create attendance records")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Attendance records created"),
        @ApiResponse(responseCode = "400", description = "Validation failed", content = @Content),
        @ApiResponse(responseCode = "403", description = "User is not allowed to create attendance", content = @Content)
    })
    public ResponseEntity<List<AttendanceResponseDTO>> createAttendanceBatch(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Attendance records to create", required = true) @Valid @RequestBody AttendanceBatchRequestDTO dto, @AuthenticationPrincipal AuthenticatedUser user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(attendanceService.createAttendanceBatch(dto, user));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an attendance record")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Attendance record deleted"),
        @ApiResponse(responseCode = "403", description = "User is not allowed to delete attendance", content = @Content),
        @ApiResponse(responseCode = "404", description = "Attendance record not found", content = @Content)
    })
    public ResponseEntity<Void> deleteAttendance(@Parameter(description = "Attendance record ID", required = true) @PathVariable UUID id, @AuthenticationPrincipal AuthenticatedUser user) {
        attendanceService.deleteAttendance(id, user);
        return ResponseEntity.noContent().build();
    }
}
