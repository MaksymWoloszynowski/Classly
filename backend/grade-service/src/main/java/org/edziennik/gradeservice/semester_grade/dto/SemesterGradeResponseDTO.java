package org.edziennik.gradeservice.semester_grade.dto;

import lombok.*;
import org.edziennik.gradeservice.semester_grade.entity.SemesterGradeType;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SemesterGradeResponseDTO {
    private UUID id;
    private int grade;
    private SemesterGradeType type;
    private UUID classificationPeriod;
    private UUID studentId;
    private String studentFullName;
    private UUID teachingAssignmentId;
    private String subjectName;
}