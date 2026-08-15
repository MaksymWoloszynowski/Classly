package org.edziennik.schoolstructureservice.parent.repository;

import org.edziennik.schoolstructureservice.parent.entity.Parent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ParentRepository extends JpaRepository<Parent, UUID> {
}
