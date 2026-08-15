package org.edziennik.schoolstructureservice.teacher.repository;

import org.edziennik.schoolstructureservice.teacher.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TeacherRepository extends JpaRepository<Teacher, UUID> {
}