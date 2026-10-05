package org.classly.schoolstructureservice.teacher.repository;

import org.classly.schoolstructureservice.student.entity.Student;
import org.classly.schoolstructureservice.teacher.entity.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface TeacherRepository extends JpaRepository<Teacher, UUID> {
    Page<Teacher> findAll(Pageable pageable);

    @Query("""
        SELECT t
        FROM Teacher t
        WHERE LOWER(t.firstName) LIKE LOWER(CONCAT('%', :search, '%'))
            OR LOWER(t.lastName) LIKE LOWER(CONCAT('%', :search, '%'))
    """)
    Page<Teacher> search(String search, Pageable pageable);

    List<Teacher> findByIdIn(Set<UUID> ids);
}