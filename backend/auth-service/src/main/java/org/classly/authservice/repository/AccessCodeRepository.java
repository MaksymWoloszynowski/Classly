package org.classly.authservice.repository;

import org.classly.authservice.entity.AccessCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface AccessCodeRepository
        extends JpaRepository<AccessCode, UUID>,
        JpaSpecificationExecutor<AccessCode> {

    Optional<AccessCode> findByCode(String code);

    Optional<AccessCode> findByRefId(UUID refId);
}