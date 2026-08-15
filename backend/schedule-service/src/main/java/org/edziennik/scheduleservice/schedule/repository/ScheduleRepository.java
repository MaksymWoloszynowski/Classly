package org.edziennik.scheduleservice.schedule.repository;

import org.edziennik.scheduleservice.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ScheduleRepository extends JpaRepository<Schedule, UUID> {
    List<Schedule> findByTeachingAssignmentIdInAndValidFromLessThanEqualAndValidToGreaterThanEqual(List<UUID> assignmentIds, LocalDate from, LocalDate to);
}
