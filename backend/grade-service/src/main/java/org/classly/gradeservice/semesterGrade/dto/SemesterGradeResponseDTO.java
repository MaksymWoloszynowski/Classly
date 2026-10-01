package org.classly.gradeservice.semesterGrade.dto;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import org.classly.gradeservice.semesterGrade.entity.SemesterGradeType;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Semester grade returned by the grade service")
public class SemesterGradeResponseDTO {
    @Schema(description = "Semester grade identifier")
    private UUID id;
    @Schema(description = "School grade")
    private int grade;
    @Schema(description = "Semester grade type")
    private SemesterGradeType type;
    @Schema(description = "Classification period identifier")
    private UUID classificationPeriod;
    @Schema(description = "Student identifier")
    private UUID studentId;
    @Schema(description = "Student full name")
    private String studentFullName;
    @Schema(description = "Teaching assignment identifier")
    private UUID teachingAssignmentId;
    @Schema(description = "Subject name")
    private String subjectName;
}