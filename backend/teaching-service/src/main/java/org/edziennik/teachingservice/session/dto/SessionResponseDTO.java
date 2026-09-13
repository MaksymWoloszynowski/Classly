package org.edziennik.teachingservice.session.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SessionResponseDTO {
    private UUID id;
    private UUID scheduleId;
    private UUID teachingAssignmentId;
    private String subjectName;
    private String teacherName;
    private String description;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
}
