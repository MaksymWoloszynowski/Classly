package org.edziennik.authservice.repository;

import org.edziennik.authservice.entity.AccessCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AccessCodeRepository extends JpaRepository<AccessCode, UUID> {
    Optional<AccessCode> findByCode(String code);
}