package org.edziennik.schoolstructureservice.teacher.service;

import org.edziennik.schoolstructureservice.teacher.dto.TeacherRequestDTO;
import org.edziennik.schoolstructureservice.teacher.dto.TeacherResponseDTO;
import org.edziennik.schoolstructureservice.teacher.entity.Teacher;
import org.edziennik.schoolstructureservice.teacher.exception.TeacherNotFoundException;
import org.edziennik.schoolstructureservice.teacher.mapper.TeacherMapper;
import org.edziennik.schoolstructureservice.teacher.repository.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TeacherService {
    private final TeacherRepository teacherRepository;

    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    public List<TeacherResponseDTO> getAllTeachers() {
        return teacherRepository.findAll().stream()
                .map(TeacherMapper::toDTO)
                .toList();
    }

    public TeacherResponseDTO getTeacherById(UUID teacherId) {
        Teacher teacher = getTeacher(teacherId);
        return TeacherMapper.toDTO(teacher);
    }

    public TeacherResponseDTO createTeacher(TeacherRequestDTO teacherRequestDTO) {
        Teacher teacher = teacherRepository.save(TeacherMapper.toModel(teacherRequestDTO));
        return TeacherMapper.toDTO(teacher);
    }

    public TeacherResponseDTO updateTeacher(UUID teacherId, TeacherRequestDTO teacherRequestDTO) {
        Teacher teacher = getTeacher(teacherId);

        teacher.setFirstName(teacherRequestDTO.getFirstName());
        teacher.setLastName(teacherRequestDTO.getLastName());

        Teacher updated = teacherRepository.save(teacher);
        return TeacherMapper.toDTO(updated);
    }

    public void deleteTeacher(UUID teacherId) {
        Teacher teacher = getTeacher(teacherId);
        teacherRepository.delete(teacher);
    }

    private Teacher getTeacher(UUID teacherId) {
        return teacherRepository.findById(teacherId)
                .orElseThrow(() ->
                        new TeacherNotFoundException("Teacher not found with ID: " + teacherId));
    }
}