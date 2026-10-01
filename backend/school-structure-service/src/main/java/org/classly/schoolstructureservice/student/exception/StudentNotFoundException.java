package org.classly.schoolstructureservice.student.exception;

import org.classly.schoolstructureservice.exception.NotFoundException;

public class StudentNotFoundException extends NotFoundException {
    public StudentNotFoundException(String message) {
        super(message);
    }
}
