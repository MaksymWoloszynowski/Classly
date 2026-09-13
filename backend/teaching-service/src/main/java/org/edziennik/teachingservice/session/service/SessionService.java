package org.edziennik.teachingservice.session.service;

import org.edziennik.schoolstructureservice.grpc.TeachingAssignmentResponse;
import org.edziennik.teachingservice.grpc.SchoolStructureGrpcClient;
import org.edziennik.teachingservice.session.dto.SessionRequestDTO;
import org.edziennik.teachingservice.session.dto.SessionResponseDTO;
import org.edziennik.teachingservice.session.entity.Session;
import org.edziennik.teachingservice.session.exception.SessionAlreadyRealizedException;
import org.edziennik.teachingservice.session.exception.SessionNotFoundException;
import org.edziennik.teachingservice.session.mapper.SessionMapper;
import org.edziennik.teachingservice.session.repository.SessionRepository;
import org.edziennik.teachingservice.security.TeacherAccessService;
import org.edziennik.security.AuthenticatedUser;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class SessionService {
    private final SessionRepository sessionRepository;
    private final SchoolStructureGrpcClient schoolStructureClient;
    private final TeacherAccessService teacherAccessService;

    public SessionService(SessionRepository sessionRepository, SchoolStructureGrpcClient schoolStructureClient, TeacherAccessService teacherAccessService) {
        this.sessionRepository = sessionRepository;
        this.schoolStructureClient = schoolStructureClient;
        this.teacherAccessService = teacherAccessService;
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

    public List<SessionResponseDTO> getSessions(UUID teachingAssignmentId, LocalDate from, LocalDate to) {
        if (from != null && to != null && from.isAfter(to)) {
            throw new IllegalArgumentException("Parameter 'from' must not be after 'to'");
        }

        if (teachingAssignmentId != null && from != null && to != null) {
            return mapToDTOList(sessionRepository.findByTeachingAssignmentIdAndDateBetween(teachingAssignmentId, from, to));
        }
        if (teachingAssignmentId != null) {
            return getSessionsByTeachingAssignment(teachingAssignmentId);
        }
        if (from != null && to != null) {
            return mapToDTOList(sessionRepository.findByDateBetween(from, to));
        }
        return getAllSessions();
    }

    public List<SessionResponseDTO> getSessionsByGroupAndDate(UUID groupId, LocalDate from, LocalDate to) {
        List<UUID> teachingAssignmentIds = schoolStructureClient.getTeachingAssignmentIdsByGroup(groupId);

        return mapToDTOList(sessionRepository.findByTeachingAssignmentIdInAndDateBetween(teachingAssignmentIds, from, to));
    }

    public SessionResponseDTO createSession(SessionRequestDTO dto, AuthenticatedUser user) {
        teacherAccessService.requireAssignmentAccess(user, dto.getTeachingAssignmentId());
        sessionRepository.findByScheduleIdAndDate(dto.getScheduleId(), dto.getDate())
                .ifPresent(session -> {
                    throw new SessionAlreadyRealizedException(dto.getScheduleId(), dto.getDate());
                });

        Session saved = sessionRepository.save(SessionMapper.toModel(dto));
        return mapToDTO(saved);
    }

    public List<SessionResponseDTO> getSessionsForTeacher(AuthenticatedUser user, LocalDate from, LocalDate to) {
        List<UUID> assignmentIds = teacherAccessService.requireTeacher(user);
        return mapToDTOList(sessionRepository.findByTeachingAssignmentIdInAndDateBetween(assignmentIds, from, to));
    }

    public SessionResponseDTO updateSession(UUID id, SessionRequestDTO dto, AuthenticatedUser user) {
        Session session = getSession(id);

        teacherAccessService.requireAssignmentAccess(user, session.getTeachingAssignmentId());

        session.setDescription(dto.getDescription());

        Session updated = sessionRepository.save(session);
        return mapToDTO(updated);
    }

    public void deleteSession(UUID id, AuthenticatedUser user) {
        Session session = getSession(id);
        teacherAccessService.requireAssignmentAccess(user, session.getTeachingAssignmentId());

        sessionRepository.delete(session);
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
