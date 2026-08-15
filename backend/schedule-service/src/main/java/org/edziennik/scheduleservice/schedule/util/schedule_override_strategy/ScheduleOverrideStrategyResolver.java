package org.edziennik.scheduleservice.schedule.util.schedule_override_strategy;

import org.edziennik.scheduleservice.schedule_override.entity.OverrideType;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ScheduleOverrideStrategyResolver {
    private final Map<OverrideType, ScheduleOverrideStrategy> strategies;

    public ScheduleOverrideStrategyResolver(ScheduleCancelledOverrideStrategy cancelled,
                                            ScheduleSubstitutionOverrideStrategy substitution,
                                            ScheduleRoomChangeOverrideStrategy roomChange) {
        this.strategies = Map.of(
                OverrideType.CANCELLED, cancelled,
                OverrideType.SUBSTITUTION, substitution,
                OverrideType.ROOM_CHANGE, roomChange
        );
    }

    public ScheduleOverrideStrategy resolve(OverrideType type) {
        return strategies.get(type);
    }
}