package org.edziennik.teachingservice.assessment.dto;

import lombok.*;
import org.edziennik.teachingservice.assessment.entity.AssessmentType;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssessmentResponseDTO {
    private UUID id;
    private UUID teachingAssignmentId;
    private String subjectName;
    private String teacherName;
    private String groupName;
    private LocalDate dateMade;
    private LocalDate dateDue;
    private AssessmentType type;
    private String description;
}