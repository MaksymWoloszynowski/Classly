package org.edziennik.scheduleservice.schedule.dto;

import lombok.*;
import org.edziennik.scheduleservice.schedule_override.entity.ScheduleOverride;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleResponseDTO {
    private UUID id;
    private UUID teachingAssignmentId;
    private String teacherName;
    private String subjectName;
    private String groupName;
    private LocalDate validTo;
    private LocalDate validFrom;
    private LocalTime startTime;
    private LocalTime endTime;
    private String room;
    private int dayOfWeek;
}
