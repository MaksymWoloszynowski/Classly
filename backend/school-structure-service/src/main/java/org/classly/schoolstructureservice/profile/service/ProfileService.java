package org.classly.schoolstructureservice.profile.service;

import org.classly.schoolstructureservice.parent.entity.Parent;
import org.classly.schoolstructureservice.parent.exception.ParentNotFoundException;
import org.classly.schoolstructureservice.parent.mapper.ParentMapper;
import org.classly.schoolstructureservice.parent.repository.ParentRepository;
import org.classly.schoolstructureservice.profile.dto.ProfileResponseDTO;
import org.classly.schoolstructureservice.student.entity.Student;
import org.classly.schoolstructureservice.student.exception.StudentNotFoundException;
import org.classly.schoolstructureservice.student.mapper.StudentMapper;
import org.classly.schoolstructureservice.student.repository.StudentRepository;
import org.classly.schoolstructureservice.teacher.entity.Teacher;
import org.classly.schoolstructureservice.teacher.exception.TeacherNotFoundException;
import org.classly.schoolstructureservice.teacher.mapper.TeacherMapper;
import org.classly.schoolstructureservice.teacher.repository.TeacherRepository;
import org.classly.security.AuthenticatedUser;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProfileService {
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final ParentRepository parentRepository;

    public ProfileService(StudentRepository studentRepository, TeacherRepository teacherRepository, ParentRepository parentRepository) {
        this.studentRepository = studentRepository;
        this.parentRepository = parentRepository;
        this.teacherRepository = teacherRepository;
    }

    public ProfileResponseDTO getProfile(AuthenticatedUser user) {
        switch (user.role()) {
            case "ROLE_STUDENT" -> {
                return buildStudentProfile(user);
            }
            case "ROLE_PARENT" -> {
                return buildParentProfile(user);
            }
            case "ROLE_TEACHER" -> {
                return buildTeacherProfile(user);
            }
            case "ROLE_ADMIN" -> {
                return buildAdminProfile(user);
            }

            default -> throw new IllegalStateException("Unsupported role: " + user.role());
        }
    }

    private ProfileResponseDTO buildStudentProfile(AuthenticatedUser user) {
        UUID id = user.refId();
        Student student = studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundException("Student not found with ID: " + id));;

        return ProfileResponseDTO.builder()
                .userId(id)
                .role(user.role())
                .student(StudentMapper.toDTO(student))
                .build();
    }

    private ProfileResponseDTO buildTeacherProfile(AuthenticatedUser user) {
        UUID id = user.refId();
        Teacher teacher = teacherRepository.findById(id).orElseThrow(() -> new TeacherNotFoundException("Teacher not found with ID: " + id));;

        return ProfileResponseDTO.builder()
                .userId(id)
                .role(user.role())
                .teacher(TeacherMapper.toDTO(teacher))
                .build();
    }

    private ProfileResponseDTO buildParentProfile(AuthenticatedUser user) {
        UUID id = user.refId();
        Parent parent = parentRepository.findById(id).orElseThrow(() -> new ParentNotFoundException("Parent not found with ID: " + id));;

        return ProfileResponseDTO.builder()
                .userId(id)
                .role(user.role())
                .parent(ParentMapper.toDTO(parent))
                .build();
    }

    private ProfileResponseDTO buildAdminProfile(AuthenticatedUser user) {
        UUID id = user.refId();

        return ProfileResponseDTO.builder()
                .userId(id)
                .role(user.role())
                .build();
    }
}
