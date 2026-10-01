package org.classly.scheduleservice.schedule.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.classly.scheduleservice.schedule_override.dto.ScheduleOverrideResponseDTO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "A resolved schedule occurrence for a specific date")
public class ScheduleOccurrenceDTO {
    @Schema(description = "Recurring schedule ID")
    private UUID scheduleId;
    @Schema(description = "Occurrence date", example = "2026-09-16")
    private LocalDate date;
    @Schema(description = "Occurrence start time", example = "08:00")
    private LocalTime startTime;
    @Schema(description = "Occurrence end time", example = "08:45")
    private LocalTime endTime;
    @Schema(description = "Teaching assignment ID")
    private UUID teachingAssignmentId;
    @Schema(description = "Subject name")
    private String subjectName;
    @Schema(description = "Teacher name")
    private String teacherName;
    @Schema(description = "Group name")
    private String groupName;
    @Schema(description = "Room where the lesson takes place", example = "Room 12")
    private String room;
    @Schema(description = "Override applied to this occurrence, if present")
    private ScheduleOverrideResponseDTO override;
}
