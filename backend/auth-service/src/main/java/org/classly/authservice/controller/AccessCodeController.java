package org.classly.authservice.controller;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.groups.Default;
import org.classly.authservice.dto.AccessCodeRequestDTO;
import org.classly.authservice.dto.AccessCodeResponseDTO;
import org.classly.authservice.service.AccessCodeService;
import org.classly.authservice.util.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/auth/access-code")
public class AccessCodeController {
    private final AccessCodeService accessCodeService;

    public AccessCodeController(AccessCodeService accessCodeService) {
        this.accessCodeService = accessCodeService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PageResponse<AccessCodeResponseDTO>> getAccessCodes(
            @RequestParam(required = true) String role,
            @RequestParam(required = false) Boolean used,
            @PageableDefault(size = 10) Pageable pageable) {

        Page<AccessCodeResponseDTO> accessCodes =
                accessCodeService.getAccessCodes(pageable, role, used);

        PageResponse<AccessCodeResponseDTO> response = new PageResponse<>(
                accessCodes.getContent(),
                accessCodes.getNumber(),
                accessCodes.getSize(),
                accessCodes.getTotalElements(),
                accessCodes.getTotalPages()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AccessCodeResponseDTO> getAccessCodeById(@PathVariable UUID id) {
        return ResponseEntity.ok().body(accessCodeService.getAccessCodeById(id));
    }

    @GetMapping("/ref/{refId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AccessCodeResponseDTO> getAccessCodeByRefId(@PathVariable UUID refId) {
        return ResponseEntity.ok().body(accessCodeService.getAccessCodeByRefId(refId));
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AccessCodeResponseDTO> createAccessCode(@Validated({Default.class}) @RequestBody AccessCodeRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(accessCodeService.createAccessCode(requestDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteAccessCode(@PathVariable UUID id) {
        accessCodeService.deleteAccessCode(id);

        return ResponseEntity.noContent().build();
    }
}
