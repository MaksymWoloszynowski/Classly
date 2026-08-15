package org.edziennik.scheduleservice.schedule.util.schedule_override_strategy;

import org.edziennik.scheduleservice.schedule.dto.ScheduleOccurrenceDTO;
import org.edziennik.scheduleservice.schedule_override.entity.ScheduleOverride;

public interface ScheduleOverrideStrategy {
    void apply(ScheduleOccurrenceDTO dto, ScheduleOverride override);
}
