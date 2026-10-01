package org.classly.schoolstructureservice.admin.service;

import org.classly.schoolstructureservice.parent.dto.AdminParentResponseDTO;
import org.classly.schoolstructureservice.parent.service.ParentService;
import org.classly.schoolstructureservice.student.dto.AdminStudentResponseDTO;
import org.classly.schoolstructureservice.student.service.StudentService;
import org.classly.schoolstructureservice.teacher.dto.AdminTeacherResponseDTO;
import org.classly.schoolstructureservice.teacher.service.TeacherService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AdminService {
    private final StudentService studentService;
    private final TeacherService teacherService;
    private final ParentService parentService;

    public AdminService(StudentService studentService, TeacherService teacherService, ParentService parentService) {
        this.studentService = studentService;
        this.parentService = parentService;
        this.teacherService = teacherService;
    }

    public Page<AdminStudentResponseDTO> getStudents(String search, Pageable pageable) {
        return studentService.getAllStudentsAdmin(search, pageable);
    }

    public AdminStudentResponseDTO getStudentById(UUID id) {
        return studentService.getStudentByIdAdmin(id);
    }

    public Page<AdminParentResponseDTO> getParents(String search, Pageable pageable) {
        return parentService.getAllParentsAdmin(search, pageable);
    }

    public AdminParentResponseDTO getParentById(UUID id) {
        return parentService.getParentByIdAdmin(id);
    }

    public Page<AdminTeacherResponseDTO> getTeachers(String search, Pageable pageable) {
        return teacherService.getAllTeachersAdmin(search, pageable);
    }

    public AdminTeacherResponseDTO getTeacherById(UUID id) {
        return teacherService.getTeacherByIdAdmin(id);
    }

}
