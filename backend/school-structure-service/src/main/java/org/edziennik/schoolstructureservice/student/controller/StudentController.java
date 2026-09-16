package org.edziennik.schoolstructureservice.student.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.edziennik.schoolstructureservice.student.dto.StudentRequestDTO;
import org.edziennik.schoolstructureservice.student.dto.StudentResponseDTO;
import org.edziennik.schoolstructureservice.student.service.StudentService;
import org.edziennik.schoolstructureservice.subject.dto.SubjectResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/student")
@Tag(name = "Students", description = "Manage students")
@SecurityRequirement(name = "cookieAuth")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<List<StudentResponseDTO>> getAllStudents(@RequestParam(required = false) UUID groupId) {
        List<StudentResponseDTO> students = groupId == null
                ? studentService.getAllStudents()
                : studentService.getStudentsByGroup(groupId);

        return ResponseEntity.ok().body(students);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> getStudent(@PathVariable UUID id) {
        StudentResponseDTO studentResponseDTO = studentService.getStudentById(id);

        return ResponseEntity.ok().body(studentResponseDTO);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StudentResponseDTO> createStudent(@Valid @RequestBody StudentRequestDTO studentRequestDTO) {
        StudentResponseDTO studentResponseDTO = studentService.createStudent(studentRequestDTO);

        return ResponseEntity.ok().body(studentResponseDTO);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StudentResponseDTO> updateStudent(@PathVariable UUID id, @Valid @RequestBody StudentRequestDTO studentRequestDTO) {
        StudentResponseDTO studentResponseDTO = studentService.updateStudent(id, studentRequestDTO);

        return ResponseEntity.ok().body(studentResponseDTO);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteStudent(@PathVariable UUID id) {
        studentService.deleteStudent(id);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/group")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StudentResponseDTO> deleteStudentFromGroup(@PathVariable UUID id) {
        StudentResponseDTO studentResponseDTO = studentService.deleteStudentFromGroup(id);

        return ResponseEntity.ok().body(studentResponseDTO);
    }

    @PostMapping("/{studentId}/group/{groupId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StudentResponseDTO> addStudentToGroup(@PathVariable UUID studentId, @PathVariable UUID groupId) {
        StudentResponseDTO studentResponseDTO = studentService.addStudentToGroup(studentId, groupId);

        return ResponseEntity.ok().body(studentResponseDTO);
    }
}
