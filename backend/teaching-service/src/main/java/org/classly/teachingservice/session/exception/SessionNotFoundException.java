package org.classly.teachingservice.session.exception;

import org.classly.teachingservice.exception.NotFoundException;

public class SessionNotFoundException extends NotFoundException {
    public SessionNotFoundException(String message) {
        super(message);
    }
}