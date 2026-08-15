package org.edziennik.scheduleservice.schedule.entity;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleOccurrence {
    private UUID scheduleId;
    private UUID teachingAssignmentId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String room;
}