package org.classly.scheduleservice.additional_schedule.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
@Schema(description = "Data required to create an additional one-off lesson")
public class AdditionalScheduleRequestDTO {
    @NotNull
    @Schema(description = "Teaching assignment ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private UUID teachingAssignmentId;

    @NotNull
    @Schema(description = "Lesson date", example = "2026-09-16", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDate date;

    @NotNull
    @Schema(description = "Lesson start time", example = "08:00", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalTime startTime;

    @NotNull
    @Schema(description = "Lesson end time", example = "08:45", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalTime endTime;

    @NotBlank
    @Schema(description = "Room where the lesson takes place", example = "Room 12", requiredMode = Schema.RequiredMode.REQUIRED)
    private String room;
}