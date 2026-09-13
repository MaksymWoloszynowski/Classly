package org.edziennik.schoolstructureservice.student.repository;

import org.edziennik.schoolstructureservice.student.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface StudentRepository extends JpaRepository<Student, UUID> {
    @Query("""
            SELECT s
            FROM Student s
            LEFT JOIN FETCH s.group
        """)
    List<Student> findAllWithGroup();

    List<Student> findByGroupId(UUID groupId);

    List<Student> findByIdIn(Set<UUID> ids);
}