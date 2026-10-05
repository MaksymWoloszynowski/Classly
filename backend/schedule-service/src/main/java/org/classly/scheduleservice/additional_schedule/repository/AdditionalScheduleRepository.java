package org.classly.scheduleservice.additional_schedule.repository;

import org.classly.scheduleservice.additional_schedule.entity.AdditionalSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface AdditionalScheduleRepository extends JpaRepository<AdditionalSchedule, UUID> {
    List<AdditionalSchedule> findByTeachingAssignmentIdInAndDateBetween(List<UUID> teachingAssignmentIds, LocalDate from, LocalDate to);
    List<AdditionalSchedule> findByTeachingAssignmentIdInAndDate(List<UUID> teachingAssignmentIds, LocalDate date);

}