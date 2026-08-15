package org.edziennik.teachingservice.session.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SessionResponseDTO {
    private UUID id;
    private UUID teachingAssignmentId;
    private String subjectName;
    private String teacherName;
    private String description;
    private LocalDate date;
}
