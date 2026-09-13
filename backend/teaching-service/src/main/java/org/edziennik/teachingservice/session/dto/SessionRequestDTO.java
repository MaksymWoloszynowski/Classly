package org.edziennik.teachingservice.session.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
public class SessionRequestDTO {
    @NotNull
    private UUID scheduleId;

    @NotNull
    private UUID teachingAssignmentId;

    private String description;

    @NotNull
    private LocalDate date;

    @NotNull
    private LocalTime startTime;

    @NotNull
    private LocalTime endTime;
}
