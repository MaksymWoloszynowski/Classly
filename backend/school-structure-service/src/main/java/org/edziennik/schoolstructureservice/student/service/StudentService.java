package org.edziennik.schoolstructureservice.student.service;

import org.edziennik.schoolstructureservice.group.entity.Group;
import org.edziennik.schoolstructureservice.group.exception.GroupNotFoundException;
import org.edziennik.schoolstructureservice.group.repository.GroupRepository;
import org.edziennik.schoolstructureservice.student.dto.StudentRequestDTO;
import org.edziennik.schoolstructureservice.student.dto.StudentResponseDTO;
import org.edziennik.schoolstructureservice.student.entity.Student;
import org.edziennik.schoolstructureservice.student.exception.StudentNotFoundException;
import org.edziennik.schoolstructureservice.student.mapper.StudentMapper;
import org.edziennik.schoolstructureservice.student.repository.StudentRepository;
import org.edziennik.schoolstructureservice.subject.dto.SubjectResponseDTO;
import org.edziennik.schoolstructureservice.subject.entity.Subject;
import org.edziennik.schoolstructureservice.subject.mapper.SubjectMapper;
import org.edziennik.schoolstructureservice.teachingassignment.entity.TeachingAssignment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final GroupRepository groupRepository;

    public StudentService(StudentRepository studentRepository, GroupRepository groupRepository) {
        this.studentRepository = studentRepository;
        this.groupRepository = groupRepository;
    }

    public List<StudentResponseDTO> getAllStudents() {
        return studentRepository.findAll().stream().map(StudentMapper::toDTO).collect(Collectors.toList());
    }

    public StudentResponseDTO getStudentById(UUID studentId) {
        Student student = getStudent(studentId);
        return StudentMapper.toDTO(student);
    }

    public Set<SubjectResponseDTO> getStudentSubjects(UUID studentId) {
        Student student = getStudent(studentId);

        if (student.getGroup() == null) {
            return Set.of();
        }

        Set<TeachingAssignment> assignments = student.getGroup().getTeachingAssignments();
        Set<Subject> subjects = assignments.stream().map(TeachingAssignment::getSubject).collect(Collectors.toSet());

        return subjects.stream().map(SubjectMapper::toDTO).collect(Collectors.toSet());
    }

    public StudentResponseDTO createStudent(StudentRequestDTO studentRequestDTO) {
        Group group = null;
        if (studentRequestDTO.getGroupId() != null) {
            group = getGroup(studentRequestDTO.getGroupId());
        }

        Student student = StudentMapper.toModel(studentRequestDTO);
        student.setGroup(group);

        Student saved = studentRepository.save(student);
        return StudentMapper.toDTO(saved);
    }

    @Transactional
    public StudentResponseDTO updateStudent(UUID studentId, StudentRequestDTO studentRequestDTO) {
        Student student = getStudent(studentId);

        student.setFirstName(studentRequestDTO.getFirstName());
        student.setLastName(studentRequestDTO.getLastName());

        if (studentRequestDTO.getGroupId() != null) {
            student.setGroup(getGroup(studentRequestDTO.getGroupId()));
        }

        return StudentMapper.toDTO(student);
    }

    public void deleteStudent(UUID studentId) {
        Student student = getStudent(studentId);
        studentRepository.delete(student);
    }

    @Transactional
    public StudentResponseDTO deleteStudentFromGroup(UUID studentId) {
        Student student = getStudent(studentId);
        student.setGroup(null);
        return StudentMapper.toDTO(student);
    }

    @Transactional
    public StudentResponseDTO addStudentToGroup(UUID studentId, UUID groupId) {
        Student student = getStudent(studentId);
        Group group = getGroup(groupId);
        student.setGroup(group);
        return StudentMapper.toDTO(student);
    }

    private Student getStudent(UUID studentId) {
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with ID: " + studentId));
    }

    private Group getGroup(UUID groupId) {
        return groupRepository.findById(groupId)
                .orElseThrow(() -> new GroupNotFoundException("Group not found with ID: " + groupId));
    }
}