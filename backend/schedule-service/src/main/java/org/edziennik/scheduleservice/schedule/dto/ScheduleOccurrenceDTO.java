package org.edziennik.scheduleservice.schedule.dto;

import lombok.*;
import org.edziennik.scheduleservice.schedule.entity.OccurrenceStatus;
import org.edziennik.scheduleservice.schedule.entity.ScheduleOccurrence;
import org.edziennik.scheduleservice.schedule_override.dto.ScheduleOverrideResponseDTO;
import org.edziennik.scheduleservice.schedule_override.entity.ScheduleOverride;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleOccurrenceDTO {
    private LocalDate date;
    private int dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
    private String subjectName;
    private String teacherName;
    private String room;
    private ScheduleOverrideResponseDTO override;
}