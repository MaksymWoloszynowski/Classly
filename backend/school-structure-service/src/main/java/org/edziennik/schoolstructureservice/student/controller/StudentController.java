package org.edziennik.schoolstructureservice.student.controller;

import jakarta.validation.Valid;
import org.edziennik.schoolstructureservice.student.dto.StudentRequestDTO;
import org.edziennik.schoolstructureservice.student.dto.StudentResponseDTO;
import org.edziennik.schoolstructureservice.student.service.StudentService;
import org.edziennik.schoolstructureservice.subject.dto.SubjectResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<List<StudentResponseDTO>> getAllStudents() {
        List<StudentResponseDTO> students = studentService.getAllStudents();

        return ResponseEntity.ok().body(students);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> getStudent(@PathVariable UUID id) {
        StudentResponseDTO studentResponseDTO = studentService.getStudentById(id);

        return ResponseEntity.ok().body(studentResponseDTO);
    }

    @GetMapping("/{id}/subjects")
    public ResponseEntity<Set<SubjectResponseDTO>> getStudentSubjects(@PathVariable UUID id) {
        Set<SubjectResponseDTO> subjects = studentService.getStudentSubjects(id);

        return ResponseEntity.ok().body(subjects);
    }

    @PostMapping
    public ResponseEntity<StudentResponseDTO> createStudent(@Valid @RequestBody StudentRequestDTO studentRequestDTO) {
        StudentResponseDTO studentResponseDTO = studentService.createStudent(studentRequestDTO);

        return ResponseEntity.ok().body(studentResponseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> updateStudent(@PathVariable UUID id, @Valid @RequestBody StudentRequestDTO studentRequestDTO) {
        StudentResponseDTO studentResponseDTO = studentService.updateStudent(id, studentRequestDTO);

        return ResponseEntity.ok().body(studentResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable UUID id) {
        studentService.deleteStudent(id);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/group")
    public ResponseEntity<StudentResponseDTO> deleteStudentFromGroup(@PathVariable UUID id) {
        StudentResponseDTO studentResponseDTO = studentService.deleteStudentFromGroup(id);

        return ResponseEntity.ok().body(studentResponseDTO);
    }

    @PostMapping("/{studentId}/group/{groupId}")
    public ResponseEntity<StudentResponseDTO> addStudentToGroup(@PathVariable UUID studentId, @PathVariable UUID groupId) {
        StudentResponseDTO studentResponseDTO = studentService.addStudentToGroup(studentId, groupId);

        return ResponseEntity.ok().body(studentResponseDTO);
    }
}