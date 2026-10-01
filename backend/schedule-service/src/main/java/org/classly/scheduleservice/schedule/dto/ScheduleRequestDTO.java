package org.classly.scheduleservice.schedule.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
@Schema(description = "Data required to create or update a recurring schedule")
public class ScheduleRequestDTO {
    @NotNull
    @Schema(description = "Teaching assignment ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private UUID teachingAssignmentId;

    @NotNull
    @Min(1)
    @Max(6)
    @Schema(description = "Day of week from Monday (1) to Saturday (6)", example = "1", minimum = "1", maximum = "6", requiredMode = Schema.RequiredMode.REQUIRED)
    private int dayOfWeek;

    @NotNull
    @Schema(description = "Recurring lesson start time", example = "08:00", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalTime startTime;

    @NotNull
    @Schema(description = "Recurring lesson end time", example = "08:45", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalTime endTime;

    @NotBlank
    @Schema(description = "Room where the lesson takes place", example = "Room 12", requiredMode = Schema.RequiredMode.REQUIRED)
    private String room;

    @Schema(description = "First date on which the schedule is valid", example = "2026-09-01")
    private LocalDate validFrom;

    @Schema(description = "Last date on which the schedule is valid", example = "2027-06-30")
    private LocalDate validTo;
}
