package org.classly.teachingservice.session.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Teaching session returned by the service")
public class SessionResponseDTO {
    @Schema(description = "Session ID")
    private UUID id;
    @Schema(description = "Schedule ID")
    private UUID scheduleId;
    @Schema(description = "Teaching assignment ID")
    private UUID teachingAssignmentId;
    @Schema(description = "Subject name")
    private String subjectName;
    @Schema(description = "Teacher name")
    private String teacherName;
    @Schema(description = "Session description", example = "Lesson on quadratic equations")
    private String description;
    @Schema(description = "Session date", example = "2026-09-16")
    private LocalDate date;
    @Schema(description = "Session start time", example = "08:00")
    private LocalTime startTime;
    @Schema(description = "Session end time", example = "08:45")
    private LocalTime endTime;
}
