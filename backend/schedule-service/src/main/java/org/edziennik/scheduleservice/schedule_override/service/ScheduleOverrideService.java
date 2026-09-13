package org.edziennik.scheduleservice.schedule_override.service;

import net.devh.boot.grpc.client.inject.GrpcClient;
import org.edziennik.scheduleservice.grpc.SchoolStructureGrpcClient;
import org.edziennik.scheduleservice.schedule.dto.ScheduleResponseDTO;
import org.edziennik.scheduleservice.schedule.entity.Schedule;
import org.edziennik.scheduleservice.schedule.exception.ScheduleNotFoundException;
import org.edziennik.scheduleservice.schedule.mapper.ScheduleMapper;
import org.edziennik.scheduleservice.schedule.repository.ScheduleRepository;
import org.edziennik.scheduleservice.schedule_override.dto.ScheduleOverrideRequestDTO;
import org.edziennik.scheduleservice.schedule_override.dto.ScheduleOverrideResponseDTO;
import org.edziennik.scheduleservice.schedule_override.entity.ScheduleOverride;
import org.edziennik.scheduleservice.schedule_override.exception.ScheduleOverrideNotFoundException;
import org.edziennik.scheduleservice.schedule_override.mapper.ScheduleOverrideMapper;
import org.edziennik.scheduleservice.schedule_override.repository.ScheduleOverrideRepository;
import org.edziennik.schoolstructureservice.grpc.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ScheduleOverrideService {
    private final SchoolStructureGrpcClient schoolStructureClient;
    private final ScheduleOverrideRepository scheduleOverrideRepository;
    private final ScheduleRepository scheduleRepository;

    public ScheduleOverrideService(ScheduleOverrideRepository scheduleOverrideRepository,
                                   ScheduleRepository scheduleRepository, SchoolStructureGrpcClient schoolStructureClient) {
        this.scheduleOverrideRepository = scheduleOverrideRepository;
        this.scheduleRepository = scheduleRepository;
        this.schoolStructureClient = schoolStructureClient;
    }

    public List<ScheduleOverrideResponseDTO> getAllOverrides() {
        return mapToDTOList(scheduleOverrideRepository.findAll());
    }

    public ScheduleOverrideResponseDTO getOverrideById(UUID id) {
        return mapToDTO(getOverride(id));
    }

    public ScheduleOverrideResponseDTO createOverride(ScheduleOverrideRequestDTO dto) {
        Schedule schedule = getSchedule(dto.getScheduleId());

        ScheduleOverride override = ScheduleOverride.builder()
                .schedule(schedule)
                .date(dto.getDate())
                .type(dto.getType())
                .newRoom(dto.getNewRoom())
                .build();

        ScheduleOverride saved = scheduleOverrideRepository.save(override);
        return mapToDTO(saved);
    }

    public ScheduleOverrideResponseDTO updateOverride(UUID id, ScheduleOverrideRequestDTO dto) {
        ScheduleOverride override = getOverride(id);

        override.setSchedule(getSchedule(dto.getScheduleId()));
        override.setDate(dto.getDate());
        override.setType(dto.getType());
        override.setNewRoom(dto.getNewRoom());

        ScheduleOverride updated = scheduleOverrideRepository.save(override);
        return mapToDTO(updated);
    }

    public void deleteOverride(UUID id) {
        scheduleOverrideRepository.delete(getOverride(id));
    }

    private ScheduleOverrideResponseDTO mapToDTO(ScheduleOverride override) {
        TeachingAssignmentResponse teachingAssignmentResponse = schoolStructureClient.getGrpcTeachingAssignment(override.getSubstituteTeachingAssignmentId());

        ScheduleOverrideResponseDTO responseDTO = ScheduleOverrideMapper.toDTO(override);

        responseDTO.setSubstituteSubjectName(teachingAssignmentResponse.getSubject());
        responseDTO.setSubstituteTeacherName(teachingAssignmentResponse.getTeacher());

        return responseDTO;
    }

    private List<ScheduleOverrideResponseDTO> mapToDTOList(List<ScheduleOverride> schedule) {
        Set<UUID> ids = schedule.stream().map(ScheduleOverride::getSubstituteTeachingAssignmentId).collect(Collectors.toSet());
        Map<UUID, TeachingAssignmentResponse> assignmentDataById = schoolStructureClient.getTeachingAssignments(ids);

        return schedule.stream().map(scheduleEntry -> {
            ScheduleOverrideResponseDTO response = ScheduleOverrideMapper.toDTO(scheduleEntry);

            response.setSubstituteSubjectName(assignmentDataById.get(scheduleEntry.getSubstituteTeachingAssignmentId()).getSubject());
            response.setSubstituteTeacherName(assignmentDataById.get(scheduleEntry.getSubstituteTeachingAssignmentId()).getTeacher());

            return response;
        }).collect(Collectors.toList());
    }

    private ScheduleOverride getOverride(UUID id) {
        return scheduleOverrideRepository.findById(id)
                .orElseThrow(() -> new ScheduleOverrideNotFoundException("Schedule override not found with ID: " + id));
    }

    private Schedule getSchedule(UUID scheduleId) {
        return scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ScheduleNotFoundException(scheduleId));
    }
}