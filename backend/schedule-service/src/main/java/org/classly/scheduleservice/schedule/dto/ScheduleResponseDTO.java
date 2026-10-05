package org.classly.scheduleservice.schedule.dto;

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
@Schema(description = "Recurring schedule returned by the service")
public class ScheduleResponseDTO {
    @Schema(description = "Schedule ID")
    private UUID id;
    @Schema(description = "Teaching assignment ID")
    private UUID teachingAssignmentId;
    @Schema(description = "Teacher name")
    private String teacherName;
    @Schema(description = "Subject name")
    private String subjectName;
    @Schema(description = "Group name")
    private String groupName;
    @Schema(description = "Last date on which the schedule is valid", example = "2027-06-30")
    private LocalDate validTo;
    @Schema(description = "First date on which the schedule is valid", example = "2026-09-01")
    private LocalDate validFrom;
    @Schema(description = "Recurring lesson start time", example = "08:00")
    private LocalTime startTime;
    @Schema(description = "Recurring lesson end time", example = "08:45")
    private LocalTime endTime;
    @Schema(description = "Room where the lesson takes place", example = "12")
    private String room;
    @Schema(description = "Day of week from Monday (1) to Friday (5)", example = "1")
    private int dayOfWeek;
}
