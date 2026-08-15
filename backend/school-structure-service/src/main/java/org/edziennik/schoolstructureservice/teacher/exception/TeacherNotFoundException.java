package org.edziennik.schoolstructureservice.teacher.exception;

import org.edziennik.schoolstructureservice.exception.NotFoundException;

public class TeacherNotFoundException extends NotFoundException {
    public TeacherNotFoundException(String message) {
        super(message);
    }
}
