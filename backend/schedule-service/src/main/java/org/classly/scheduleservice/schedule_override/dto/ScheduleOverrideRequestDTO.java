package org.classly.scheduleservice.schedule_override.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import org.classly.scheduleservice.schedule_override.entity.OverrideType;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Data required to create or update a schedule override")
public class ScheduleOverrideRequestDTO {
    @NotNull
    @Schema(description = "Schedule ID affected by the override", requiredMode = Schema.RequiredMode.REQUIRED)
    private UUID scheduleId;

    @NotNull
    @Schema(description = "Date affected by the override", example = "2026-09-16", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDate date;

    @NotNull
    @Schema(description = "Type of schedule override", requiredMode = Schema.RequiredMode.REQUIRED)
    private OverrideType type;

    @Schema(description = "Replacement teaching assignment ID")
    private UUID substituteTeachingAssignmentId;

    @Schema(description = "Replacement room", example = "Room 12")
    private String newRoom;
}