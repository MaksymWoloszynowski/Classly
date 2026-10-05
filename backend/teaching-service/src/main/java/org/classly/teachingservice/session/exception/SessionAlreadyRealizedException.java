package org.classly.teachingservice.session.exception;

import java.time.LocalDate;
import java.util.UUID;

public class SessionAlreadyRealizedException extends RuntimeException {
    public SessionAlreadyRealizedException(UUID scheduleId, LocalDate date) {
        super("Session has already been realized for schedule " + scheduleId + " on " + date);
    }
}
