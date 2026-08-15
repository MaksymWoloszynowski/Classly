package org.edziennik.scheduleservice.additional_schedule.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdditionalScheduleResponseDTO {
    private UUID id;
    private UUID teachingAssignmentId;
    private String subjectName;
    private String teacherName;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String room;
}