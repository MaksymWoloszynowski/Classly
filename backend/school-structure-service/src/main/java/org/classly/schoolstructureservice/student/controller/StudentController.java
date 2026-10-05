package org.classly.schoolstructureservice.student.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.classly.schoolstructureservice.student.dto.StudentRequestDTO;
import org.classly.schoolstructureservice.student.dto.StudentResponseDTO;
import org.classly.schoolstructureservice.student.dto.StudentSummaryDTO;
import org.classly.schoolstructureservice.student.service.StudentService;
import org.classly.schoolstructureservice.util.PageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.net.URI;

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
    @Operation(summary = "List students", description = "Returns a paginated and sorted list of students or students belonging to a group.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Students returned"),
        @ApiResponse(responseCode = "401", description = "Authentication required", content = @Content)
    })
    public ResponseEntity<PageResponse<StudentResponseDTO>> getAllStudents(
        @Parameter(description = "Group ID") @RequestParam(required = false) UUID groupId,
        @Parameter(description = "Search by first name or last name") @RequestParam(required = false) String search,
        @PageableDefault(size = 10, sort = "lastName", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<StudentResponseDTO> students = groupId == null
            ? studentService.getAllStudents(search, pageable)
            : studentService.getStudentsByGroup(groupId, search, pageable);

        PageResponse<StudentResponseDTO> response = new PageResponse<>(
            students.getContent(),
            students.getNumber(),
            students.getSize(),
            students.getTotalElements(),
            students.getTotalPages()
        );

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/summary")
    @Operation(summary = "List students in shortened form", description = "Returns a paginated and sorted list of students in shortened form.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Students returned"),
            @ApiResponse(responseCode = "401", description = "Authentication required", content = @Content)
    })
    public ResponseEntity<PageResponse<StudentSummaryDTO>> getAllStudentsSummary(
            @Parameter(description = "Search by first name or last name") @RequestParam(required = false) String search,
            @PageableDefault(size = 10, sort = "lastName", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<StudentSummaryDTO> students = studentService.getAllStudentsSummary(search, pageable);

        PageResponse<StudentSummaryDTO> response = new PageResponse<>(
                students.getContent(),
                students.getNumber(),
                students.getSize(),
                students.getTotalElements(),
                students.getTotalPages()
        );

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a student")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Student returned", content = @Content(schema = @Schema(implementation = StudentResponseDTO.class))),
        @ApiResponse(responseCode = "401", description = "Authentication required", content = @Content),
        @ApiResponse(responseCode = "404", description = "Student not found", content = @Content)
    })
    public ResponseEntity<StudentResponseDTO> getStudent(@Parameter(description = "Student ID", required = true) @PathVariable UUID id) {
        StudentResponseDTO studentResponseDTO = studentService.getStudentById(id);

        return ResponseEntity.ok().body(studentResponseDTO);
    }

    @PostMapping
    @Operation(summary = "Create a student")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Student created", content = @Content(schema = @Schema(implementation = StudentResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Validation failed", content = @Content),
        @ApiResponse(responseCode = "401", description = "Authentication required", content = @Content),
        @ApiResponse(responseCode = "403", description = "Admin role required", content = @Content)
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StudentResponseDTO> createStudent(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Student to create", required = true) @Valid @RequestBody StudentRequestDTO studentRequestDTO) {
        StudentResponseDTO studentResponseDTO = studentService.createStudent(studentRequestDTO);

        return ResponseEntity.created(URI.create("/student/" + studentResponseDTO.getId())).body(studentResponseDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a student")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Student updated", content = @Content(schema = @Schema(implementation = StudentResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Validation failed", content = @Content),
        @ApiResponse(responseCode = "401", description = "Authentication required", content = @Content),
        @ApiResponse(responseCode = "403", description = "Admin role required", content = @Content),
        @ApiResponse(responseCode = "404", description = "Student not found", content = @Content)
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StudentResponseDTO> updateStudent(@Parameter(description = "Student ID", required = true) @PathVariable UUID id, @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated student data", required = true) @Valid @RequestBody StudentRequestDTO studentRequestDTO) {
        StudentResponseDTO studentResponseDTO = studentService.updateStudent(id, studentRequestDTO);

        return ResponseEntity.ok().body(studentResponseDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a student")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Student deleted"),
        @ApiResponse(responseCode = "401", description = "Authentication required", content = @Content),
        @ApiResponse(responseCode = "403", description = "Admin role required", content = @Content),
        @ApiResponse(responseCode = "404", description = "Student not found", content = @Content)
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteStudent(@Parameter(description = "Student ID", required = true) @PathVariable UUID id) {
        studentService.deleteStudent(id);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/group")
    @Operation(summary = "Remove a student from a group")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Student removed from group", content = @Content(schema = @Schema(implementation = StudentResponseDTO.class))),
        @ApiResponse(responseCode = "401", description = "Authentication required", content = @Content),
        @ApiResponse(responseCode = "403", description = "Admin role required", content = @Content),
        @ApiResponse(responseCode = "404", description = "Student not found", content = @Content)
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StudentResponseDTO> deleteStudentFromGroup(@Parameter(description = "Student ID", required = true) @PathVariable UUID id) {
        StudentResponseDTO studentResponseDTO = studentService.deleteStudentFromGroup(id);

        return ResponseEntity.ok().body(studentResponseDTO);
    }

    @PostMapping("/{studentId}/group/{groupId}")
    @Operation(summary = "Add a student to a group")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Student added to group", content = @Content(schema = @Schema(implementation = StudentResponseDTO.class))),
        @ApiResponse(responseCode = "401", description = "Authentication required", content = @Content),
        @ApiResponse(responseCode = "403", description = "Admin role required", content = @Content),
        @ApiResponse(responseCode = "404", description = "Student or group not found", content = @Content)
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StudentResponseDTO> addStudentToGroup(@Parameter(description = "Student ID", required = true) @PathVariable UUID studentId, @Parameter(description = "Group ID", required = true) @PathVariable UUID groupId) {
        StudentResponseDTO studentResponseDTO = studentService.addStudentToGroup(studentId, groupId);

        return ResponseEntity.ok().body(studentResponseDTO);
    }
}
