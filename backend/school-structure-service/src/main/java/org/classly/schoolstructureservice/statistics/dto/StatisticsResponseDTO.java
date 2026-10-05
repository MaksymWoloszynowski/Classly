package org.classly.schoolstructureservice.statistics.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Summary counts for the school structure")
public record StatisticsResponseDTO(
        @Schema(description = "Number of students")
        long students,
        @Schema(description = "Number of teachers")
        long teachers,
        @Schema(description = "Number of parents")
        long parents,
        @Schema(description = "Number of groups")
        long groups,
        @Schema(description = "Number of subjects")
        long subjects,
        @Schema(description = "Number of teaching assignments")
        long teachingAssignments,
        @Schema(description = "Number of classification periods")
        long classificationPeriods) {
}
