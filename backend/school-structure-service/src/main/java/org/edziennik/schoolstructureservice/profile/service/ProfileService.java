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
                return buildStudentProfile(user.refId());
            }
            case "ROLE_PARENT" -> {
                return buildParentProfile(user.refId());
            }
            case "ROLE_TEACHER" -> {
                return buildTeacherProfile(user.refId());
            }

            default -> throw new IllegalStateException("Unsupported role: " + user.role());
        }
    }

    private ProfileResponseDTO buildStudentProfile(UUID id) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundException("Student not found with ID: " + id));;

        return ProfileResponseDTO.builder()
                            .userId(id)
                            .student(StudentMapper.toDTO(student))
                            .build();
    }

    private ProfileResponseDTO buildTeacherProfile(UUID id) {
        Teacher teacher = teacherRepository.findById(id).orElseThrow(() -> new TeacherNotFoundException("Teacher not found with ID: " + id));;

        return ProfileResponseDTO.builder()
                .userId(id)
                .teacher(TeacherMapper.toDTO(teacher))
                .build();
    }

    private ProfileResponseDTO buildParentProfile(UUID id) {
        Parent parent = parentRepository.findById(id).orElseThrow(() -> new ParentNotFoundException("Parent not found with ID: " + id));;

        return ProfileResponseDTO.builder()
                .userId(id)
                .parent(ParentMapper.toDTO(parent))
                .build();
    }
}
