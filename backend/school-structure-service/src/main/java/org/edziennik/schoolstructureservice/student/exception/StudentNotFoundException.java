package org.edziennik.schoolstructureservice.student.exception;

import org.edziennik.schoolstructureservice.exception.NotFoundException;

public class StudentNotFoundException extends NotFoundException {
    public StudentNotFoundException(String message) {
        super(message);
    }
}
