package org.classly.teachingservice.session.repository;

import org.classly.teachingservice.session.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SessionRepository extends JpaRepository<Session, UUID> {
    List<Session> findByTeachingAssignmentId(UUID teachingAssignmentId);
    List<Session> findByTeachingAssignmentIdAndDateBetween(UUID teachingAssignmentId, LocalDate from, LocalDate to);
    List<Session> findByTeachingAssignmentIdInAndDateBetween(List<UUID> teachingAssignmentId, LocalDate from, LocalDate to);
    List<Session> findByDateBetween(LocalDate from, LocalDate to);
    Optional<Session> findByScheduleIdAndDate(UUID scheduleId, LocalDate date);
}
