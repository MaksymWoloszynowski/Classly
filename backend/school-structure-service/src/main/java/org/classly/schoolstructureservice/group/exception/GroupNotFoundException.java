package org.classly.schoolstructureservice.group.exception;

import org.classly.schoolstructureservice.exception.NotFoundException;

public class GroupNotFoundException extends NotFoundException {
    public GroupNotFoundException(String message) {
        super(message);
    }
}
