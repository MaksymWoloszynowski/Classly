package org.classly.schoolstructureservice.parent.repository;

import org.classly.schoolstructureservice.parent.entity.Parent;
import org.classly.schoolstructureservice.student.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface ParentRepository extends JpaRepository<Parent, UUID> {
	Page<Parent> findAllBy(Pageable pageable);

	@Query("""
			SELECT p
			FROM Parent p
			WHERE LOWER(p.firstName) LIKE LOWER(CONCAT('%', :search, '%'))
			   OR LOWER(p.lastName) LIKE LOWER(CONCAT('%', :search, '%'))
		""")
	Page<Parent> search(String search, Pageable pageable);

	List<Parent> findByIdIn(Set<UUID> ids);
}
