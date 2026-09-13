package org.edziennik.scheduleservice.schedule.dto;

import lombok.*;
import org.edziennik.scheduleservice.schedule_override.dto.ScheduleOverrideResponseDTO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleOccurrenceDTO {
    private UUID scheduleId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private UUID teachingAssignmentId;
    private String subjectName;
    private String teacherName;
    private String groupName;
    private String room;
    private ScheduleOverrideResponseDTO override;
}
