package org.classly.scheduleservice.schedule.repository;

import org.classly.scheduleservice.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ScheduleRepository extends JpaRepository<Schedule, UUID> {
    @Query("""
    SELECT s
    FROM Schedule s
    WHERE s.teachingAssignmentId IN :assignmentIds
      AND s.validFrom <= :to
      AND s.validTo >= :from
""")
    List<Schedule> findOverlapping(
            @Param("assignmentIds") List<UUID> assignmentIds,
            @Param("from") LocalDate from,
            @Param("to") LocalDate to
    );}
