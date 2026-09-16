package org.edziennik.gradeservice.grade.dto;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import org.edziennik.gradeservice.grade.entity.GradeType;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Grade summary used in category responses")
public class GradeSummaryDTO {
    @Schema(description = "Grade ID")
    private UUID id;

    @Schema(description = "Grade value")
    private double grade;

    @Schema(description = "Grade creation date", example = "2026-09-16")
    private LocalDate date;

    @Schema(description = "Student ID")
    private UUID studentId;

    @Schema(description = "Student full name")
    private String studentFullName;
}
