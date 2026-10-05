package org.classly.schoolstructureservice.subject.repository;

import org.classly.schoolstructureservice.subject.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SubjectRepository extends JpaRepository<Subject, UUID> {

}