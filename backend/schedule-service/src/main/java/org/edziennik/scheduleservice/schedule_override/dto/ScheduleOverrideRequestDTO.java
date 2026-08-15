package org.edziennik.scheduleservice.schedule_override.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import org.edziennik.scheduleservice.schedule_override.entity.OverrideType;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class ScheduleOverrideRequestDTO {
    @NotNull
    private UUID scheduleId;

    @NotNull
    private LocalDate date;

    @NotNull
    private OverrideType type;

    private UUID substituteTeacherId;

    private UUID substituteSubjectId;

    private String newRoom;
}