package org.classly.schoolstructureservice.student.repository;

import org.classly.schoolstructureservice.student.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface StudentRepository extends JpaRepository<Student, UUID> {
    Page<Student> findAllBy(Pageable pageable);
    Page<Student> findByGroupId(UUID groupId, Pageable pageable);

    @Query("""
            SELECT s
            FROM Student s
            LEFT JOIN s.group g
            WHERE LOWER(s.firstName) LIKE LOWER(CONCAT('%', :search, '%'))
               OR LOWER(s.lastName) LIKE LOWER(CONCAT('%', :search, '%'))
        """)
    Page<Student> search(String search, Pageable pageable);

    @Query("""
            SELECT s
            FROM Student s
            LEFT JOIN s.group g
            WHERE s.group.id = :groupId
                    AND (LOWER(s.firstName) LIKE LOWER(CONCAT('%', :search, '%'))
                    OR LOWER(s.lastName) LIKE LOWER(CONCAT('%', :search, '%'))
                    OR LOWER(COALESCE(g.name, '')) LIKE LOWER(CONCAT('%', :search, '%')))
            """)
    Page<Student> searchByGroupId(UUID groupId, String search, Pageable pageable);

    List<Student> findByGroupId(UUID groupId);
    List<Student> findByIdIn(Set<UUID> ids);
}