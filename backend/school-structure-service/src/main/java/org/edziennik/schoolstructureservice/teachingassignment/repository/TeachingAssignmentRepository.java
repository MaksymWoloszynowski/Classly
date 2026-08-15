package org.edziennik.schoolstructureservice.teachingassignment.repository;

import org.edziennik.schoolstructureservice.teachingassignment.entity.TeachingAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.UUID;

public interface TeachingAssignmentRepository extends JpaRepository<TeachingAssignment, UUID>, JpaSpecificationExecutor<TeachingAssignment> {
    List<TeachingAssignment> findByGroupId(UUID groupId);
}