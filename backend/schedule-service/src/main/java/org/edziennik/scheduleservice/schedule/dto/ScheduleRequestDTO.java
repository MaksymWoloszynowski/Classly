package org.edziennik.scheduleservice.schedule.dto;

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
public class ScheduleRequestDTO {
    @NotNull
    private UUID teachingAssignmentId;

    @NotNull
    @Min(1)
    @Max(6)
    private int dayOfWeek;

    @NotNull
    private LocalTime startTime;

    @NotNull
    private LocalTime endTime;

    @NotBlank
    private String room;

    private LocalDate validFrom;

    private LocalDate validTo;
}
