package org.edziennik.scheduleservice.schedule_override.dto;

import lombok.*;
import org.edziennik.scheduleservice.schedule_override.entity.OverrideType;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleOverrideResponseDTO {
    private UUID id;
    private UUID scheduleId;
    private LocalDate date;
    private OverrideType type;
    private UUID substituteTeachingAssignmentId;
    private String substituteTeacherName;
    private String substituteSubjectName;
    private String newRoom;
}