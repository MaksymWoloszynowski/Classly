package org.edziennik.scheduleservice.schedule_override.repository;

import org.edziennik.scheduleservice.schedule_override.entity.ScheduleOverride;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ScheduleOverrideRepository extends JpaRepository<ScheduleOverride, UUID> {
    List<ScheduleOverride> findByScheduleIdInAndDateBetween(List<UUID> scheduleId, LocalDate start, LocalDate end);
    List<ScheduleOverride> findByScheduleIdInAndDate(List<UUID> scheduleId, LocalDate date);
}
