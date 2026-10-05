package org.classly.teachingservice.attendance.repository;

import org.classly.teachingservice.attendance.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.UUID;

public interface AttendanceRepository extends JpaRepository<Attendance, UUID>, JpaSpecificationExecutor<Attendance> {
    List<Attendance> findBySessionIdInAndStudentId(List<UUID> sessionIds, UUID studentId);
}