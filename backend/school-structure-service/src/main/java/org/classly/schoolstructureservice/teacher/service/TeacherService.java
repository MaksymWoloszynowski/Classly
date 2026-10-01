package org.classly.schoolstructureservice.teacher.service;

import org.classly.events.UserCreatedEvent;
import org.classly.schoolstructureservice.kafka.UserEventProducer;
import org.classly.schoolstructureservice.teacher.dto.AdminTeacherResponseDTO;
import org.classly.schoolstructureservice.teacher.dto.TeacherRequestDTO;
import org.classly.schoolstructureservice.teacher.dto.TeacherResponseDTO;
import org.classly.schoolstructureservice.teacher.entity.Teacher;
import org.classly.schoolstructureservice.teacher.exception.TeacherNotFoundException;
import org.classly.schoolstructureservice.teacher.mapper.TeacherMapper;
import org.classly.schoolstructureservice.teacher.repository.TeacherRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TeacherService {
    private final TeacherRepository teacherRepository;
    private final UserEventProducer producer;

    public TeacherService(TeacherRepository teacherRepository, UserEventProducer producer) {
        this.teacherRepository = teacherRepository;
        this.producer = producer;
    }

    public Page<TeacherResponseDTO> getAllTeachers(String search, Pageable pageable) {
        Page<Teacher> teachers = getTeachers(search, pageable);

        return teachers.map(TeacherMapper::toDTO);
    }

    public Page<AdminTeacherResponseDTO> getAllTeachersAdmin(String search, Pageable pageable) {
        Page<Teacher> teachers = getTeachers(search, pageable);

        return teachers.map(TeacherMapper::toAdminDTO);
    }

    public TeacherResponseDTO getTeacherById(UUID teacherId) {
        Teacher teacher = getTeacher(teacherId);
        return TeacherMapper.toDTO(teacher);
    }

    public AdminTeacherResponseDTO getTeacherByIdAdmin(UUID teacherId) {
        Teacher teacher = getTeacher(teacherId);
        return TeacherMapper.toAdminDTO(teacher);
    }

    public TeacherResponseDTO createTeacher(TeacherRequestDTO teacherRequestDTO) {
        Teacher teacher = teacherRepository.save(TeacherMapper.toModel(teacherRequestDTO));
        UserCreatedEvent event = UserCreatedEvent.newBuilder()
                .setId(teacher.getId().toString())
                .setRole("ROLE_TEACHER")
                .build();
        producer.send(event);
        return TeacherMapper.toDTO(teacher);
    }

    public TeacherResponseDTO updateTeacher(UUID teacherId, TeacherRequestDTO teacherRequestDTO) {
        Teacher teacher = getTeacher(teacherId);

        teacher.setFirstName(teacherRequestDTO.getFirstName());
        teacher.setLastName(teacherRequestDTO.getLastName());
        teacher.setSocialId(teacherRequestDTO.getSocialId());

        Teacher updated = teacherRepository.save(teacher);
        return TeacherMapper.toDTO(updated);
    }

    public void deleteTeacher(UUID teacherId) {
        Teacher teacher = getTeacher(teacherId);
        teacherRepository.delete(teacher);
    }

    private Page<Teacher> getTeachers(String search, Pageable pageable) {
        return search == null || search.isBlank()
                ? teacherRepository.findAll(pageable)
                : teacherRepository.search(search.trim(), pageable);
    }

    private Teacher getTeacher(UUID teacherId) {
        return teacherRepository.findById(teacherId)
                .orElseThrow(() ->
                        new TeacherNotFoundException("Teacher not found with ID: " + teacherId));
    }
}