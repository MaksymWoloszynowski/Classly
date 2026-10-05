package org.classly.scheduleservice.additional_schedule.mapper;

import org.classly.scheduleservice.additional_schedule.dto.AdditionalScheduleRequestDTO;
import org.classly.scheduleservice.additional_schedule.dto.AdditionalScheduleResponseDTO;
import org.classly.scheduleservice.additional_schedule.entity.AdditionalSchedule;

public class AdditionalScheduleMapper {
    public static AdditionalScheduleResponseDTO toDTO(AdditionalSchedule schedule) {
        return AdditionalScheduleResponseDTO.builder()
                .id(schedule.getId())
                .teachingAssignmentId(schedule.getTeachingAssignmentId())
                .date(schedule.getDate())
                .startTime(schedule.getStartTime())
                .endTime(schedule.getEndTime())
                .room(schedule.getRoom())
                .build();
    }

    public static AdditionalSchedule toModel(AdditionalScheduleRequestDTO dto) {
        return AdditionalSchedule.builder()
                .teachingAssignmentId(dto.getTeachingAssignmentId())
                .date(dto.getDate())
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .room(dto.getRoom())
                .build();
    }
}