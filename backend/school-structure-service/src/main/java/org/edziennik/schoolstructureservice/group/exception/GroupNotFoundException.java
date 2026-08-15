package org.edziennik.schoolstructureservice.group.exception;

import org.edziennik.schoolstructureservice.exception.NotFoundException;

public class GroupNotFoundException extends NotFoundException {
    public GroupNotFoundException(String message) {
        super(message);
    }
}
