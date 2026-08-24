package org.edziennik.teachingservice.session.repository;

import org.edziennik.teachingservice.session.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface SessionRepository extends JpaRepository<Session, UUID> {
    List<Session> findByTeachingAssignmentId(UUID teachingAssignmentId);
    List<Session> findByTeachingAssignmentIdInAndDateBetween(List<UUID> teachingAssignmentId, LocalDate from, LocalDate to);
}