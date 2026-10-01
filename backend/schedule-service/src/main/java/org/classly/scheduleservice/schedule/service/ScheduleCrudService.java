package org.classly.scheduleservice.schedule.service;

import org.classly.scheduleservice.grpc.SchoolStructureGrpcClient;
import org.classly.scheduleservice.schedule.dto.ScheduleRequestDTO;
import org.classly.scheduleservice.schedule.dto.ScheduleResponseDTO;
import org.classly.scheduleservice.schedule.entity.Schedule;
import org.classly.scheduleservice.schedule.exception.ScheduleNotFoundException;
import org.classly.scheduleservice.schedule.exception.TeachingAssignmentNotFoundException;
import org.classly.scheduleservice.schedule.mapper.ScheduleMapper;
import org.classly.scheduleservice.schedule.repository.ScheduleRepository;
import org.classly.schoolstructureservice.grpc.TeachingAssignmentResponse;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ScheduleCrudService {
    private final SchoolStructureGrpcClient schoolStructureClient;
    private final ScheduleRepository scheduleRepository;

    public ScheduleCrudService(ScheduleRepository scheduleRepository, SchoolStructureGrpcClient schoolStructureClient) {
        this.scheduleRepository = scheduleRepository;
        this.schoolStructureClient = schoolStructureClient;
    }

    public List<ScheduleResponseDTO> getAllSchedules() {
        return mapToDTOList(scheduleRepository.findAll());
    }

    public ScheduleResponseDTO getScheduleById(UUID id) {
        return mapToDTO(getSchedule(id));
    }

    public ScheduleResponseDTO createSchedule(ScheduleRequestDTO dto) {
        Schedule saved = scheduleRepository.save(ScheduleMapper.toModel(dto));
        return mapToDTO(saved);
    }

    public ScheduleResponseDTO updateSchedule(UUID id, ScheduleRequestDTO dto) {
        Schedule schedule = getSchedule(id);

        ScheduleMapper.updateModel(schedule, dto);

        Schedule updated = scheduleRepository.save(schedule);
        return mapToDTO(updated);
    }

    public void deleteSchedule(UUID id) {
        scheduleRepository.delete(getSchedule(id));
    }

    private ScheduleResponseDTO mapToDTO(Schedule schedule) {
        TeachingAssignmentResponse assignment = schoolStructureClient.getGrpcTeachingAssignment(schedule.getTeachingAssignmentId());
        return buildScheduleResponseDTO(schedule, assignment);
    }

    private List<ScheduleResponseDTO> mapToDTOList(List<Schedule> schedules) {
        Set<UUID> ids = schedules.stream().map(Schedule::getTeachingAssignmentId).collect(Collectors.toSet());
        Map<UUID, TeachingAssignmentResponse> assignmentDataById = schoolStructureClient.getTeachingAssignments(ids);

        return schedules.stream()
                .map(entry -> buildScheduleResponseDTO(entry, requireAssignment(assignmentDataById, entry.getTeachingAssignmentId())))
                .toList();
    }

    private ScheduleResponseDTO buildScheduleResponseDTO(Schedule schedule, TeachingAssignmentResponse assignment) {
        ScheduleResponseDTO dto = ScheduleMapper.toDTO(schedule);
        dto.setSubjectName(assignment.getSubject());
        dto.setTeacherName(assignment.getTeacher());
        dto.setGroupName(assignment.getGroup());
        return dto;
    }

    private TeachingAssignmentResponse requireAssignment(Map<UUID, TeachingAssignmentResponse> assignmentDataById, UUID teachingAssignmentId) {
        TeachingAssignmentResponse assignment = assignmentDataById.get(teachingAssignmentId);

        if (assignment == null) {
            throw new TeachingAssignmentNotFoundException(teachingAssignmentId);
        }

        return assignment;
    }

    private Schedule getSchedule(UUID id) {
        return scheduleRepository.findById(id)
                .orElseThrow(() -> new ScheduleNotFoundException(id));
    }
}