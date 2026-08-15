package org.edziennik.schoolstructureservice.student.repository;

import org.edziennik.schoolstructureservice.student.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface StudentRepository extends JpaRepository<Student, UUID> {
    List<Student> findByGroupId(UUID groupId);
}