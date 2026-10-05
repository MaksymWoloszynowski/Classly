package org.classly.schoolstructureservice.teacher.exception;

import org.classly.schoolstructureservice.exception.NotFoundException;

public class TeacherNotFoundException extends NotFoundException {
    public TeacherNotFoundException(String message) {
        super(message);
    }
}
