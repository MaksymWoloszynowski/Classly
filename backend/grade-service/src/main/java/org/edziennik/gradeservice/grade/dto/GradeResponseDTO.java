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
@Schema(description = "Individual grade returned by the grade service")
public class GradeResponseDTO  {
    @Schema(description = "Grade ID")
    private UUID id;

    @Schema(description = "Grade category ID")
    private UUID categoryId;

    @Schema(description = "Grade value")
    private double grade;

    @Schema(description = "Grade type")
    private GradeType type;

    @Schema(description = "Category description")
    private String description;

    @Schema(description = "Category weight from 0 to 3", example = "3", minimum = "0", maximum = "3")
    private int weight;

    @Schema(description = "Date on which the grade was given", example = "2026-09-16")
    private LocalDate date;

    @Schema(description = "Student full name")
    private String studentFullName;

    @Schema(description = "Subject name")
    private String subject;
}