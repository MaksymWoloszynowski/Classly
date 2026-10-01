package org.classly.scheduleservice.schedule_override.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.classly.scheduleservice.schedule_override.entity.OverrideType;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Schedule override returned by the service")
public class ScheduleOverrideResponseDTO {
    @Schema(description = "Schedule override ID")
    private UUID id;
    @Schema(description = "Schedule ID")
    private UUID scheduleId;
    @Schema(description = "Override date", example = "2026-09-16")
    private LocalDate date;
    @Schema(description = "Type of schedule override")
    private OverrideType type;
    @Schema(description = "Replacement teaching assignment ID")
    private UUID substituteTeachingAssignmentId;
    @Schema(description = "Replacement teacher name")
    private String substituteTeacherName;
    @Schema(description = "Replacement subject name")
    private String substituteSubjectName;
    @Schema(description = "Replacement room", example = "Room 12")
    private String newRoom;
}