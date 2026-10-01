package org.classly.teachingservice.attendance.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.classly.teachingservice.attendance.entity.AttendanceType;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Attendance record returned by the service")
public class AttendanceResponseDTO {
    @Schema(description = "Attendance record ID")
    private UUID id;
    @Schema(description = "Student ID")
    private UUID studentId;
    @Schema(description = "Student full name")
    private String studentFullName;
    @Schema(description = "Attendance type")
    private AttendanceType type;
    @Schema(description = "Subject name")
    private String subject;
    @Schema(description = "Teacher name")
    private String teacher;
    @Schema(description = "Session date", example = "2026-09-16")
    private LocalDate date;
    @Schema(description = "Session start time", example = "08:00")
    private LocalTime startTime;
    @Schema(description = "Session end time", example = "08:45")
    private LocalTime endTime;
}