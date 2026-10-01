package org.classly.schoolstructureservice.group.service;

import org.classly.schoolstructureservice.group.dto.GroupRequestDTO;
import org.classly.schoolstructureservice.group.dto.GroupResponseDTO;
import org.classly.schoolstructureservice.group.dto.GroupSummaryDTO;
import org.classly.schoolstructureservice.group.entity.Group;
import org.classly.schoolstructureservice.group.exception.GroupNotFoundException;
import org.classly.schoolstructureservice.group.mapper.GroupMapper;
import org.classly.schoolstructureservice.group.repository.GroupRepository;
import org.classly.schoolstructureservice.student.dto.StudentResponseDTO;
import org.classly.schoolstructureservice.student.entity.Student;
import org.classly.schoolstructureservice.student.mapper.StudentMapper;
import org.classly.schoolstructureservice.teacher.entity.Teacher;
import org.classly.schoolstructureservice.teacher.exception.TeacherNotFoundException;
import org.classly.schoolstructureservice.teacher.repository.TeacherRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class GroupService {
    private final GroupRepository groupRepository;
    private final TeacherRepository teacherRepository;

    public GroupService(GroupRepository groupRepository, TeacherRepository teacherRepository) {
        this.groupRepository = groupRepository;
        this.teacherRepository = teacherRepository;
    }

    public List<GroupResponseDTO> getAllGroups() {
        List<Group> groups = groupRepository.findAllWithHomeroomTeacher();
        return groups.stream().map(GroupMapper::toDTO).toList();
    }

    public List<GroupSummaryDTO> getAllGroupsSummary() {
        List<Group> groups = groupRepository.findAll();
        return groups.stream().map(GroupMapper::toSummaryDTO).toList();
    }

    public GroupResponseDTO getGroupById(UUID groupId) {
        Group group = getGroup(groupId);
        return GroupMapper.toDTO(group);
    }

    public GroupResponseDTO createGroup(GroupRequestDTO groupRequestDTO) {
        Group newGroup = groupRepository.save(GroupMapper.toModel(groupRequestDTO));
        return GroupMapper.toDTO(newGroup);
    }

    @Transactional
    public GroupResponseDTO updateGroup(UUID groupId, GroupRequestDTO groupRequestDTO) {
        Group group = getGroup(groupId);
        group.setName(groupRequestDTO.getGroupName());
        return GroupMapper.toDTO(group);
    }

    @Transactional
    public GroupResponseDTO updateHomeroomTeacher(UUID groupId, UUID teacherId) {
        Group group = getGroup(groupId);
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new TeacherNotFoundException("Teacher not found with ID: " + teacherId));

        group.setHomeroomTeacher(teacher);

        return GroupMapper.toDTO(group);
    }

    public void deleteGroup(UUID id) {
        Group group = getGroup(id);
        groupRepository.delete(group);
    }

    public Set<StudentResponseDTO> getStudentsFromGroup(UUID groupId) {
        Set<Student> students = getGroup(groupId).getStudents();
        return students.stream().map(StudentMapper::toDTO).collect(Collectors.toSet());
    }

    private Group getGroup(UUID groupId) {
        return groupRepository.findById(groupId)
                .orElseThrow(() -> new GroupNotFoundException("Group not found with ID: " + groupId));
    }
}