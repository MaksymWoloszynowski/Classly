package org.edziennik.scheduleservice.schedule_override.exception;

import org.edziennik.scheduleservice.exception.NotFoundException;

public class ScheduleOverrideNotFoundException extends NotFoundException {
    public ScheduleOverrideNotFoundException(String message) {
        super(message);
    }
}