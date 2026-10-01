package org.classly.schoolstructureservice.parent.exception;

import org.classly.schoolstructureservice.exception.NotFoundException;

public class ParentNotFoundException extends NotFoundException {
    public ParentNotFoundException(String message) {
        super(message);
    }
}
