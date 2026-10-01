package org.classly.schoolstructureservice.student.service;

import org.classly.events.UserCreatedEvent;
import org.classly.schoolstructureservice.group.entity.Group;
import org.classly.schoolstructureservice.group.exception.GroupNotFoundException;
import org.classly.schoolstructureservice.group.repository.GroupRepository;
import org.classly.schoolstructureservice.kafka.UserEventProducer;
import org.classly.schoolstructureservice.student.dto.AdminStudentResponseDTO;
import org.classly.schoolstructureservice.student.dto.StudentRequestDTO;
import org.classly.schoolstructureservice.student.dto.StudentResponseDTO;
import org.classly.schoolstructureservice.student.dto.StudentSummaryDTO;
import org.classly.schoolstructureservice.student.entity.Student;
import org.classly.schoolstructureservice.student.exception.StudentNotFoundException;
import org.classly.schoolstructureservice.student.mapper.StudentMapper;
import org.classly.schoolstructureservice.student.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final GroupRepository groupRepository;
    private final UserEventProducer producer;

    public StudentService(StudentRepository studentRepository, GroupRepository groupRepository, UserEventProducer producer) {
        this.studentRepository = studentRepository;
        this.groupRepository = groupRepository;
        this.producer = producer;
    }

    public Page<StudentResponseDTO> getAllStudents(String search, Pageable pageable) {
        Page<Student> students = getStudents(search, pageable);
        return students.map(StudentMapper::toDTO);
    }

    public Page<AdminStudentResponseDTO> getAllStudentsAdmin(String search, Pageable pageable) {
        Page<Student> students = getStudents(search, pageable);
        return students.map(StudentMapper::toAdminDTO);
    }

    public Page<StudentSummaryDTO> getAllStudentsSummary(String search, Pageable pageable) {
        Page<Student> students = getStudents(search, pageable);
        return students.map(StudentMapper::toSummaryDTO);
    }

    public Page<StudentResponseDTO> getStudentsByGroup(UUID groupId, String search, Pageable pageable) {
        Page<Student> students = search == null || search.isBlank()
                ? studentRepository.findByGroupId(groupId, pageable)
                : studentRepository.searchByGroupId(groupId, search.trim(), pageable);
        return students.map(StudentMapper::toDTO);
    }

    public StudentResponseDTO getStudentById(UUID studentId) {
        Student student = getStudent(studentId);
        return StudentMapper.toDTO(student);
    }

    public AdminStudentResponseDTO getStudentByIdAdmin(UUID studentId) {
        Student student = getStudent(studentId);
        return StudentMapper.toAdminDTO(student);
    }

    public StudentResponseDTO createStudent(StudentRequestDTO studentRequestDTO) {
        Group group = null;
        if (studentRequestDTO.getGroupId() != null) {
            group = getGroup(studentRequestDTO.getGroupId());
        }

        Student student = StudentMapper.toModel(studentRequestDTO);
        student.setGroup(group);

        Student saved = studentRepository.save(student);

        UserCreatedEvent event = UserCreatedEvent.newBuilder()
                .setId(saved.getId().toString())
                .setRole("ROLE_STUDENT")
                .build();
        producer.send(event);

        return StudentMapper.toDTO(saved);
    }

    @Transactional
    public StudentResponseDTO updateStudent(UUID studentId, StudentRequestDTO studentRequestDTO) {
        Student student = getStudent(studentId);

        student.setFirstName(studentRequestDTO.getFirstName());
        student.setLastName(studentRequestDTO.getLastName());
        student.setSocialId(studentRequestDTO.getSocialId());

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

    private Page<Student> getStudents(String search, Pageable pageable) {
        return search == null || search.isBlank()
                ? studentRepository.findAllBy(pageable)
                : studentRepository.search(search.trim(), pageable);
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
