package org.classly.scheduleservice.additional_schedule.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Additional one-off lesson returned by the service")
public class AdditionalScheduleResponseDTO {
    @Schema(description = "Additional lesson ID")
    private UUID id;
    @Schema(description = "Teaching assignment ID")
    private UUID teachingAssignmentId;
    @Schema(description = "Subject name")
    private String subjectName;
    @Schema(description = "Teacher name")
    private String teacherName;
    @Schema(description = "Lesson date", example = "2026-09-16")
    private LocalDate date;
    @Schema(description = "Lesson start time", example = "08:00")
    private LocalTime startTime;
    @Schema(description = "Lesson end time", example = "08:45")
    private LocalTime endTime;
    @Schema(description = "Room where the lesson takes place", example = "Room 12")
    private String room;
}