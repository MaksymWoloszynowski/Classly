package org.classly.scheduleservice.additional_schedule.exception;

public class AdditionalScheduleNotFoundException extends RuntimeException {
    public AdditionalScheduleNotFoundException(String message) {
        super(message);
    }
}