package org.classly.schoolstructureservice.teacher.mapper;

import org.classly.schoolstructureservice.group.dto.GroupSummaryDTO;
import org.classly.schoolstructureservice.group.mapper.GroupMapper;
import org.classly.schoolstructureservice.teacher.dto.AdminTeacherResponseDTO;
import org.classly.schoolstructureservice.teacher.dto.TeacherRequestDTO;
import org.classly.schoolstructureservice.teacher.dto.TeacherResponseDTO;
import org.classly.schoolstructureservice.teacher.entity.Teacher;
import org.classly.schoolstructureservice.teachingAssignment.dto.TeachingAssignmentResponseDTO;
import org.classly.schoolstructureservice.teachingAssignment.mapper.TeachingAssignmentMapper;

import java.util.Comparator;
import java.util.List;

public class TeacherMapper {
    public static TeacherResponseDTO toDTO(Teacher teacher) {
        return TeacherResponseDTO.builder()
                .id(teacher.getId())
                .firstName(teacher.getFirstName())
                .lastName(teacher.getLastName())
                .teachingAssignments(getTeachingAssignments(teacher))
                .homeroomGroups(getHomeroomGroups(teacher))
                .build();
    }

    public static AdminTeacherResponseDTO toAdminDTO(Teacher teacher) {
        return AdminTeacherResponseDTO.builder()
                .id(teacher.getId())
                .socialId(teacher.getSocialId())
                .firstName(teacher.getFirstName())
                .lastName(teacher.getLastName())
                .teachingAssignments(getTeachingAssignments(teacher))
                .homeroomGroups(getHomeroomGroups(teacher))
                .build();
    }

    public static Teacher toModel(TeacherRequestDTO teacherRequestDTO) {
        return Teacher.builder()
                .firstName(teacherRequestDTO.getFirstName())
                .lastName(teacherRequestDTO.getLastName())
                .socialId(teacherRequestDTO.getSocialId())
                .build();
    }

    private static List<TeachingAssignmentResponseDTO> getTeachingAssignments(Teacher teacher) {
        return teacher.getTeachingAssignments().stream()
                .map(TeachingAssignmentMapper::toDTO)
                .sorted(Comparator.comparing(TeachingAssignmentResponseDTO::getGroupName))
                .toList();
    }

    private static List<GroupSummaryDTO> getHomeroomGroups(Teacher teacher) {
        return teacher.getGroups().stream()
                .map(GroupMapper::toSummaryDTO)
                .sorted(Comparator.comparing(GroupSummaryDTO::getName))
                .toList();
    }
}

