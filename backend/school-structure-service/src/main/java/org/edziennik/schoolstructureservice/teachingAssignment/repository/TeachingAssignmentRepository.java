package org.edziennik.schoolstructureservice.teachingAssignment.repository;

import org.edziennik.schoolstructureservice.teachingAssignment.entity.TeachingAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface TeachingAssignmentRepository extends JpaRepository<TeachingAssignment, UUID>, JpaSpecificationExecutor<TeachingAssignment> {
    List<TeachingAssignment> findByGroupId(UUID groupId);
    List<TeachingAssignment> findByTeacherId(UUID teacherId);

    List<TeachingAssignment> findAllByIdIn(Set<UUID> ids);
}