package org.classly.schoolstructureservice.classificationPeriod.exception;

import org.classly.schoolstructureservice.exception.NotFoundException;

public class ClassificationPeriodNotFoundException extends NotFoundException {
    public ClassificationPeriodNotFoundException(String message) {
        super(message);
    }
}
