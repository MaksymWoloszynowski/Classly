package org.edziennik.schoolstructureservice.parent.exception;

import org.edziennik.schoolstructureservice.exception.NotFoundException;

public class ParentNotFoundException extends NotFoundException {
    public ParentNotFoundException(String message) {
        super(message);
    }
}
