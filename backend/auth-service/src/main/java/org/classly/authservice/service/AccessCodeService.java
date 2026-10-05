package org.classly.authservice.service;

import org.classly.authservice.dto.AccessCodeRequestDTO;
import org.classly.authservice.dto.AccessCodeResponseDTO;
import org.classly.authservice.entity.AccessCode;
import org.classly.authservice.entity.UserRole;
import org.classly.authservice.exception.AccessCodeNotFoundException;
import org.classly.authservice.grpc.SchoolStructureGrpcClient;
import org.classly.authservice.mapper.AccessCodeMapper;
import org.classly.authservice.repository.AccessCodeRepository;
import org.classly.schoolstructureservice.grpc.ParentResponse;
import org.classly.schoolstructureservice.grpc.StudentResponse;
import org.classly.schoolstructureservice.grpc.TeacherResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AccessCodeService {
    private static final String ALPHABET =
            "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int LENGTH = 15;
    private static final SecureRandom RANDOM = new SecureRandom();
    private final AccessCodeRepository accessCodeRepository;
    private final SchoolStructureGrpcClient schoolStructureClient;

    public AccessCodeService(AccessCodeRepository accessCodeRepository, SchoolStructureGrpcClient schoolStructureClient) {
        this.accessCodeRepository = accessCodeRepository;
        this.schoolStructureClient = schoolStructureClient;
    }

    public Page<AccessCodeResponseDTO> getAccessCodes(Pageable pageable, String role, Boolean used) {
        UserRole userRole = mapRole(role);

        Specification<AccessCode> spec = Specification.allOf();
        spec = spec.and((root, query, cb) -> cb.equal(root.get("role"), userRole));
        if (used != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("used"), used));
        }

        Page<AccessCode> accessCodes = accessCodeRepository.findAll(spec, pageable);

        return mapToDTOList(accessCodes, userRole);
    }

    public AccessCodeResponseDTO getAccessCodeById(UUID id) {
        AccessCode code = getAccessCode(id);

        return AccessCodeMapper.toDTO(code);
    }

    public AccessCodeResponseDTO getAccessCodeByRefId(UUID refId) {
        AccessCode code = accessCodeRepository
                .findByRefId(refId)
                .orElseThrow(() -> new AccessCodeNotFoundException("Access code not found with refId: " + refId));

        return AccessCodeMapper.toDTO(code);
    }

    public AccessCodeResponseDTO createAccessCode(AccessCodeRequestDTO requestDTO) {
        AccessCode code = AccessCodeMapper.toModel(requestDTO);
        code.setCode(generateAccessCode());

        AccessCode saved = accessCodeRepository.save(code);

        return AccessCodeMapper.toDTO(saved);
    }

    public void deleteAccessCode(UUID id) {
        AccessCode code = getAccessCode(id);

        accessCodeRepository.delete(code);
    }

    private String generateAccessCode() {StringBuilder code = new StringBuilder(LENGTH);
        for (int i = 0; i < LENGTH; i++) {
            code.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length()))
            );
        }

        return code.toString();
    }

    private AccessCode getAccessCode(UUID id) {
        return accessCodeRepository.findById(id)
                .orElseThrow(() -> new AccessCodeNotFoundException("Access code not found with id: " + id));
    }

    private UserRole mapRole(String role) {
        if (role == null) {
            throw new IllegalArgumentException("Role is required");
        }
        return switch (role.toUpperCase()) {
            case "STUDENT" -> UserRole.ROLE_STUDENT;
            case "PARENT" -> UserRole.ROLE_PARENT;
            case "TEACHER" -> UserRole.ROLE_TEACHER;
            default -> throw new IllegalArgumentException(
                    "Unknown user role: " + role
            );
        };
    }

    private Page<AccessCodeResponseDTO> mapToDTOList(Page<AccessCode> accessCodes, UserRole role) {
        Set<UUID> refIds = accessCodes.stream().map(AccessCode::getRefId).collect(Collectors.toSet());

        return switch (role) {
            case ROLE_STUDENT -> mapStudents(accessCodes, refIds);
            case ROLE_PARENT -> mapParents(accessCodes, refIds);
            case ROLE_TEACHER -> mapTeachers(accessCodes, refIds);
            default -> throw new IllegalArgumentException("Unsupported role for access codes: " + role);
        };
    }

    private Page<AccessCodeResponseDTO> mapStudents(Page<AccessCode> accessCodes, Set<UUID> refIds) {
        Map<UUID, StudentResponse> students = schoolStructureClient.getStudents(refIds);
        return accessCodes.map(accessCode -> {
            AccessCodeResponseDTO responseDto = AccessCodeMapper.toDTO(accessCode);
            responseDto.setName(students.get(accessCode.getRefId()).getFirstName() + " " + students.get(accessCode.getRefId()).getLastName());
            return responseDto;
        });
    }

    private Page<AccessCodeResponseDTO> mapParents(Page<AccessCode> accessCodes, Set<UUID> refIds) {
        Map<UUID, ParentResponse> parents = schoolStructureClient.getParents(refIds);
        return accessCodes.map(accessCode -> {
            AccessCodeResponseDTO responseDto = AccessCodeMapper.toDTO(accessCode);
            responseDto.setName(parents.get(accessCode.getRefId()).getName());
            return responseDto;
        });
    }

    private Page<AccessCodeResponseDTO> mapTeachers(Page<AccessCode> accessCodes, Set<UUID> refIds) {
        Map<UUID, TeacherResponse> teachers = schoolStructureClient.getTeachers(refIds);
        return accessCodes.map(accessCode -> {
            AccessCodeResponseDTO responseDto = AccessCodeMapper.toDTO(accessCode);
            responseDto.setName(teachers.get(accessCode.getRefId()).getName());
            return responseDto;
        });
    }
}