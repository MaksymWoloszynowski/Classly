package org.edziennik.teachingservice.session.service;

import org.edziennik.schoolstructureservice.grpc.TeachingAssignmentResponse;
import org.edziennik.teachingservice.grpc.SchoolStructureGrpcClient;
import org.edziennik.teachingservice.session.dto.SessionRequestDTO;
import org.edziennik.teachingservice.session.dto.SessionResponseDTO;
import org.edziennik.teachingservice.session.entity.Session;
import org.edziennik.teachingservice.session.exception.SessionNotFoundException;
import org.edziennik.teachingservice.session.mapper.SessionMapper;
import org.edziennik.teachingservice.session.repository.SessionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SessionService {
    private final SessionRepository sessionRepository;
    private final SchoolStructureGrpcClient schoolStructureClient;

    public SessionService(SessionRepository sessionRepository, SchoolStructureGrpcClient schoolStructureClient) {
        this.sessionRepository = sessionRepository;
        this.schoolStructureClient = schoolStructureClient;
    }

    public List<SessionResponseDTO> getAllSessions() {
        return mapToDTOList(sessionRepository.findAll());
    }

    public SessionResponseDTO getSessionById(UUID id) {
        return mapToDTO(getSession(id));
    }

    public List<SessionResponseDTO> getSessionsByTeachingAssignment(UUID teachingAssignmentId) {
        return mapToDTOList(sessionRepository.findByTeachingAssignmentId(teachingAssignmentId));
    }

    public SessionResponseDTO createSession(SessionRequestDTO dto) {
        Session saved = sessionRepository.save(SessionMapper.toModel(dto));
        return mapToDTO(saved);
    }

    public SessionResponseDTO updateSession(UUID id, SessionRequestDTO dto) {
        Session session = getSession(id);

        session.setTeachingAssignmentId(dto.getTeachingAssignmentId());
        session.setDescription(dto.getDescription());

        Session updated = sessionRepository.save(session);
        return mapToDTO(updated);
    }

    public void deleteSession(UUID id) {
        sessionRepository.delete(getSession(id));
    }

    private SessionResponseDTO mapToDTO(Session session) {
        TeachingAssignmentResponse assignment = schoolStructureClient.getTeachingAssignment(session.getTeachingAssignmentId());

        SessionResponseDTO dto = SessionMapper.toDTO(session);
        dto.setSubjectName(assignment.getSubject());
        dto.setTeacherName(assignment.getTeacher());

        return dto;
    }

    private List<SessionResponseDTO> mapToDTOList(List<Session> sessions) {
        return sessions.stream().map(this::mapToDTO).toList();
    }

    private Session getSession(UUID id) {
        return sessionRepository.findById(id)
                .orElseThrow(() -> new SessionNotFoundException("Session not found with ID: " + id));
    }
}