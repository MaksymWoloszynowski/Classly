package org.classly.gradeservice.grade.dto;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Grades and average grouped for a subject")
public class SubjectGradeResponseDTO {
    @Schema(description = "Grades belonging to the subject")
    private List<GradeResponseDTO> grades;

    @Schema(description = "Average grade for the subject", example = "82.25")
    private double average;
}
