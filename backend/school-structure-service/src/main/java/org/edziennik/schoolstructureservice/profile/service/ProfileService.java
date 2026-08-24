package org.edziennik.schoolstructureservice.profile.service;

import org.edziennik.schoolstructureservice.parent.entity.Parent;
import org.edziennik.schoolstructureservice.parent.exception.ParentNotFoundException;
import org.edziennik.schoolstructureservice.parent.mapper.ParentMapper;
import org.edziennik.schoolstructureservice.parent.repository.ParentRepository;
import org.edziennik.schoolstructureservice.profile.dto.ProfileResponseDTO;
import org.edziennik.schoolstructureservice.student.entity.Student;
import org.edziennik.schoolstructureservice.student.exception.StudentNotFoundException;
import org.edziennik.schoolstructureservice.student.mapper.StudentMapper;
import org.edziennik.schoolstructureservice.student.repository.StudentRepository;
import org.edziennik.schoolstructureservice.teacher.entity.Teacher;
import org.edziennik.schoolstructureservice.teacher.exception.TeacherNotFoundException;
import org.edziennik.schoolstructureservice.teacher.mapper.TeacherMapper;
import org.edziennik.schoolstructureservice.teacher.repository.TeacherRepository;
import org.edziennik.security.AuthenticatedUser;
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
}
