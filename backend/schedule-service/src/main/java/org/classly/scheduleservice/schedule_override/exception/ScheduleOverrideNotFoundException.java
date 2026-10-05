package org.classly.scheduleservice.schedule_override.exception;

import org.classly.scheduleservice.exception.NotFoundException;

public class ScheduleOverrideNotFoundException extends NotFoundException {
    public ScheduleOverrideNotFoundException(String message) {
        super(message);
    }
}