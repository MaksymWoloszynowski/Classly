package org.classly.scheduleservice.schedule.exception;

import org.classly.scheduleservice.exception.NotFoundException;

import java.util.UUID;

public class ScheduleNotFoundException extends NotFoundException {
    public ScheduleNotFoundException(UUID scheduleId) {
        super("Schedule not found: " + scheduleId);
    }
}