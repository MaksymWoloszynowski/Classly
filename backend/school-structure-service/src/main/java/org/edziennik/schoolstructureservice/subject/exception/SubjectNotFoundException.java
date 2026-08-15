package org.edziennik.schoolstructureservice.subject.exception;

import org.edziennik.schoolstructureservice.exception.NotFoundException;

public class SubjectNotFoundException extends NotFoundException {
    public SubjectNotFoundException(String message) {
        super(message);
    }
}
