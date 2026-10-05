package org.classly.scheduleservice.schedule_override.repository;

import org.classly.scheduleservice.schedule_override.entity.ScheduleOverride;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ScheduleOverrideRepository extends JpaRepository<ScheduleOverride, UUID> {
    List<ScheduleOverride> findByScheduleIdInAndDateBetween(List<UUID> scheduleId, LocalDate from, LocalDate to);
    List<ScheduleOverride> findBySubstituteTeachingAssignmentIdInAndDateBetween(List<UUID> teachingAssignmentIds, LocalDate from, LocalDate to);
}
