package org.edziennik.teachingservice.session.controller;

import jakarta.validation.Valid;
import org.edziennik.teachingservice.session.dto.SessionRequestDTO;
import org.edziennik.teachingservice.session.dto.SessionResponseDTO;
import org.edziennik.teachingservice.session.service.SessionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/session")
public class SessionController {
    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping
    public ResponseEntity<List<SessionResponseDTO>> getSessions(
            @RequestParam(required = false) UUID teachingAssignmentId) {

        if (teachingAssignmentId != null) {
            return ResponseEntity.ok(sessionService.getSessionsByTeachingAssignment(teachingAssignmentId));
        }
        return ResponseEntity.ok(sessionService.getAllSessions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SessionResponseDTO> getSessionById(@PathVariable UUID id) {
        return ResponseEntity.ok(sessionService.getSessionById(id));
    }

    @GetMapping("/date")
    public ResponseEntity<List<SessionResponseDTO>> getSessionsByGroupAndDate(
            @RequestParam(required = true) UUID groupId,
            @RequestParam(required = true) LocalDate from,
            @RequestParam(required = true) LocalDate to
            ) {

        return ResponseEntity.ok(sessionService.getSessionsByGroupAndDate(groupId, from, to));
    }

    @PostMapping
    public ResponseEntity<SessionResponseDTO> createSession(@Valid @RequestBody SessionRequestDTO dto) {
        return ResponseEntity.ok(sessionService.createSession(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SessionResponseDTO> updateSession(@PathVariable UUID id, @Valid @RequestBody SessionRequestDTO dto) {
        return ResponseEntity.ok(sessionService.updateSession(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSession(@PathVariable UUID id) {
        sessionService.deleteSession(id);
        return ResponseEntity.noContent().build();
    }
}