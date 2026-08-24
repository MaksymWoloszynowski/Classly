package org.edziennik.schoolstructureservice.classificationPeriod.exception;

import org.edziennik.schoolstructureservice.exception.NotFoundException;

public class ClassificationPeriodNotFoundException extends NotFoundException {
    public ClassificationPeriodNotFoundException(String message) {
        super(message);
    }
}
