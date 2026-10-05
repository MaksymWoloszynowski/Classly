package org.classly.teachingservice.session.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
@Schema(description = "Data required to create or update a teaching session")
public class SessionRequestDTO {
    @NotNull
    @Schema(description = "Schedule ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private UUID scheduleId;

    @NotNull
    @Schema(description = "Teaching assignment ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private UUID teachingAssignmentId;

    @Schema(description = "Session description", example = "Lesson on quadratic equations")
    private String description;

    @NotNull
    @Schema(description = "Session date", example = "2026-09-16", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDate date;

    @NotNull
    @Schema(description = "Session start time", example = "08:00", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalTime startTime;

    @NotNull
    @Schema(description = "Session end time", example = "08:45", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalTime endTime;
}
