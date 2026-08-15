package org.edziennik.scheduleservice.schedule.util.schedule_override_strategy;

import org.edziennik.scheduleservice.schedule.dto.ScheduleOccurrenceDTO;
import org.edziennik.scheduleservice.schedule.entity.OccurrenceStatus;
import org.edziennik.scheduleservice.schedule_override.entity.ScheduleOverride;
import org.springframework.stereotype.Component;

@Component
public class ScheduleCancelledOverrideStrategy implements ScheduleOverrideStrategy {
    @Override
    public void apply(ScheduleOccurrenceDTO dto, ScheduleOverride override) {
    }
}
