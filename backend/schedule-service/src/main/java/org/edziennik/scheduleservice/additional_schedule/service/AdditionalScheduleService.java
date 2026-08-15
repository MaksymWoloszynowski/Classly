package org.edziennik.scheduleservice.additional_schedule.service;

import org.edziennik.scheduleservice.additional_schedule.dto.AdditionalScheduleRequestDTO;
import org.edziennik.scheduleservice.additional_schedule.dto.AdditionalScheduleResponseDTO;
import org.edziennik.scheduleservice.additional_schedule.entity.AdditionalSchedule;
import org.edziennik.scheduleservice.additional_schedule.exception.AdditionalScheduleNotFoundException;
import org.edziennik.scheduleservice.additional_schedule.mapper.AdditionalScheduleMapper;
import org.edziennik.scheduleservice.additional_schedule.repository.AdditionalScheduleRepository;
import org.edziennik.scheduleservice.grpc.SchoolStructureGrpcClient;
import org.edziennik.schoolstructureservice.grpc.TeachingAssignmentResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AdditionalScheduleService {
    private final AdditionalScheduleRepository additionalSessionRepository;
    private final SchoolStructureGrpcClient schoolStructureClient;

    public AdditionalScheduleService(AdditionalScheduleRepository additionalSessionRepository,
                                    SchoolStructureGrpcClient schoolStructureClient) {
        this.additionalSessionRepository = additionalSessionRepository;
        this.schoolStructureClient = schoolStructureClient;
    }

    public List<AdditionalScheduleResponseDTO> getAllAdditionalSessions() {
        return mapToDTOList(additionalSessionRepository.findAll());
    }

    public AdditionalScheduleResponseDTO getAdditionalSessionById(UUID id) {
        return mapToDTO(getAdditionalSession(id));
    }

    public AdditionalScheduleResponseDTO createAdditionalSession(AdditionalScheduleRequestDTO dto) {
        AdditionalSchedule saved = additionalSessionRepository.save(AdditionalScheduleMapper.toModel(dto));
        return mapToDTO(saved);
    }

    public AdditionalScheduleResponseDTO updateAdditionalSession(UUID id, AdditionalScheduleRequestDTO dto) {
        AdditionalSchedule session = getAdditionalSession(id);

        session.setTeachingAssignmentId(dto.getTeachingAssignmentId());
        session.setDate(dto.getDate());
        session.setStartTime(dto.getStartTime());
        session.setEndTime(dto.getEndTime());
        session.setRoom(dto.getRoom());

        AdditionalSchedule updated = additionalSessionRepository.save(session);
        return mapToDTO(updated);
    }

    public void deleteAdditionalSession(UUID id) {
        additionalSessionRepository.delete(getAdditionalSession(id));
    }

    private AdditionalScheduleResponseDTO mapToDTO(AdditionalSchedule session) {
        TeachingAssignmentResponse assignment = schoolStructureClient.getGrpcTeachingAssignment(session.getTeachingAssignmentId());

        AdditionalScheduleResponseDTO dto = AdditionalScheduleMapper.toDTO(session);
        dto.setSubjectName(assignment.getSubject());
        dto.setTeacherName(assignment.getTeacher());

        return dto;
    }

    private List<AdditionalScheduleResponseDTO> mapToDTOList(List<AdditionalSchedule> sessions) {
        return sessions.stream().map(this::mapToDTO).toList();
    }

    private AdditionalSchedule getAdditionalSession(UUID id) {
        return additionalSessionRepository.findById(id)
                .orElseThrow(() -> new AdditionalScheduleNotFoundException("Additional session not found with ID: " + id));
    }
}