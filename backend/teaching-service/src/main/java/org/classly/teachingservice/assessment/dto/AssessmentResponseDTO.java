package org.classly.teachingservice.assessment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.classly.teachingservice.assessment.entity.AssessmentType;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Assessment returned by the service")
public class AssessmentResponseDTO {
    @Schema(description = "Assessment ID")
    private UUID id;
    @Schema(description = "Teaching assignment ID")
    private UUID teachingAssignmentId;
    @Schema(description = "Subject name")
    private String subjectName;
    @Schema(description = "Teacher name")
    private String teacherName;
    @Schema(description = "Group name")
    private String groupName;
    @Schema(description = "Date when the assessment was created", example = "2026-09-16")
    private LocalDate dateMade;
    @Schema(description = "Assessment due date", example = "2026-10-15")
    private LocalDate dateDue;
    @Schema(description = "Assessment type")
    private AssessmentType type;
    @Schema(description = "Assessment description", example = "Chapter 1 test")
    private String description;
}