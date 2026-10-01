package org.classly.schoolstructureservice.group.repository;

import org.classly.schoolstructureservice.group.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

    public interface GroupRepository extends JpaRepository<Group, UUID> {
        @Query("""
            SELECT g
            FROM Group g
            LEFT JOIN FETCH g.homeroomTeacher
        """)
        List<Group> findAllWithHomeroomTeacher();
}