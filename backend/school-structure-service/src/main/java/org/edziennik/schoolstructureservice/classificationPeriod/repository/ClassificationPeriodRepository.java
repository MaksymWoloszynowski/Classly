package org.edziennik.schoolstructureservice.classificationPeriod.repository;

import org.edziennik.schoolstructureservice.classificationPeriod.entity.ClassificationPeriod;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClassificationPeriodRepository extends JpaRepository<ClassificationPeriod, UUID> {
}
