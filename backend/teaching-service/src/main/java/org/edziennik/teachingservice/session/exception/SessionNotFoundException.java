package org.edziennik.teachingservice.session.exception;

import org.edziennik.teachingservice.exception.NotFoundException;

public class SessionNotFoundException extends NotFoundException {
    public SessionNotFoundException(String message) {
        super(message);
    }
}