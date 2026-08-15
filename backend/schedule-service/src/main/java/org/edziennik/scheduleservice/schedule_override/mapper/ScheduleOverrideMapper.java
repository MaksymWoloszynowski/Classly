package org.edziennik.scheduleservice.schedule_override.mapper;

import org.edziennik.scheduleservice.schedule_override.dto.ScheduleOverrideResponseDTO;
import org.edziennik.scheduleservice.schedule_override.entity.ScheduleOverride;

public class ScheduleOverrideMapper {
    public static ScheduleOverrideResponseDTO toDTO(ScheduleOverride override) {
        return ScheduleOverrideResponseDTO.builder()
                .id(override.getId())
                .scheduleId(override.getSchedule().getId())
                .date(override.getDate())
                .type(override.getType())
                .substituteTeacherId(override.getSubstituteTeacherId())
                .newRoom(override.getNewRoom())
                .build();
    }
}