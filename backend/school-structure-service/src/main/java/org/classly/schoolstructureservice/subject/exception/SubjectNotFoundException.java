package org.classly.schoolstructureservice.subject.exception;

import org.classly.schoolstructureservice.exception.NotFoundException;

public class SubjectNotFoundException extends NotFoundException {
    public SubjectNotFoundException(String message) {
        super(message);
    }
}
