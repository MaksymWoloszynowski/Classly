package org.edziennik.scheduleservice.schedule.exception;

import org.edziennik.scheduleservice.exception.NotFoundException;

import java.util.UUID;

public class ScheduleNotFoundException extends NotFoundException {
    public ScheduleNotFoundException(UUID scheduleId) {
        super("Schedule not found: " + scheduleId);
    }
}