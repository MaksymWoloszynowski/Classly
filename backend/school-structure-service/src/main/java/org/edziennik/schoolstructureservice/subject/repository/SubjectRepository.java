package org.edziennik.schoolstructureservice.subject.repository;

import org.edziennik.schoolstructureservice.subject.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SubjectRepository extends JpaRepository<Subject, UUID> {

}