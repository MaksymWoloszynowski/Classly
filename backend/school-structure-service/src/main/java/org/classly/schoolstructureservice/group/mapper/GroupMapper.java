package org.classly.schoolstructureservice.group.mapper;

import org.classly.schoolstructureservice.group.dto.GroupRequestDTO;
import org.classly.schoolstructureservice.group.dto.GroupResponseDTO;
import org.classly.schoolstructureservice.group.dto.GroupSummaryDTO;
import org.classly.schoolstructureservice.group.entity.Group;
import org.classly.schoolstructureservice.student.dto.StudentSummaryDTO;
import org.classly.schoolstructureservice.student.mapper.StudentMapper;
import org.classly.schoolstructureservice.teachingAssignment.dto.TeachingAssignmentResponseDTO;
import org.classly.schoolstructureservice.teachingAssignment.mapper.TeachingAssignmentMapper;

import java.util.Comparator;
import java.util.stream.Collectors;

public class GroupMapper {
    public static GroupResponseDTO toDTO(Group group) {
        return GroupResponseDTO.builder()
                .id(group.getId())
                .name(group.getName())
                .students(group.getStudents().stream()
                        .map(StudentMapper::toSummaryDTO)
                        .sorted(Comparator.comparing(StudentSummaryDTO::getLastName))
                        .toList())
                .teachingAssignments(group.getTeachingAssignments().stream()
                        .map(TeachingAssignmentMapper::toDTO)
                        .sorted(Comparator.comparing(TeachingAssignmentResponseDTO::getSubjectName))
                        .toList())
                .homeroomTeacher(group.getHomeroomTeacher().getFirstName()+ " " + group.getHomeroomTeacher().getLastName())
                .build();
    }

    public static Group toModel(GroupRequestDTO groupRequestDTO) {
        return Group.builder()
                .name(groupRequestDTO.getGroupName())
                .build();
    }

    public static GroupSummaryDTO toSummaryDTO(Group group) {
        return GroupSummaryDTO.builder()
                .id(group.getId())
                .name(group.getName())
                .build();
    }
}