package org.edziennik.schoolstructureservice.group.repository;

import org.edziennik.schoolstructureservice.group.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GroupRepository extends JpaRepository<Group, UUID> {
}