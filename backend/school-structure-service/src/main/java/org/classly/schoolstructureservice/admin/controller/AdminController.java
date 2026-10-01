package org.classly.schoolstructureservice.admin.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.classly.schoolstructureservice.admin.service.AdminService;
import org.classly.schoolstructureservice.parent.dto.AdminParentResponseDTO;
import org.classly.schoolstructureservice.parent.dto.ParentResponseDTO;
import org.classly.schoolstructureservice.student.dto.AdminStudentResponseDTO;
import org.classly.schoolstructureservice.student.dto.StudentResponseDTO;
import org.classly.schoolstructureservice.teacher.dto.AdminTeacherResponseDTO;
import org.classly.schoolstructureservice.teacher.dto.TeacherResponseDTO;
import org.classly.schoolstructureservice.util.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/admin")
@Tag(name = "Admin", description = "Get expanded user info")
@SecurityRequirement(name = "cookieAuth")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/student")
    @Operation(summary = "List students", description = "Returns a paginated and sorted list of students or students belonging to a group.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Students returned"),
            @ApiResponse(responseCode = "401", description = "Authentication required", content = @Content)
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PageResponse<AdminStudentResponseDTO>> getStudents(
            @Parameter(description = "Search by first name or last name") @RequestParam(required = false) String search,
            @PageableDefault(size = 10, sort = "lastName", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<AdminStudentResponseDTO> students = adminService.getStudents(search, pageable);

        PageResponse<AdminStudentResponseDTO> response = new PageResponse<>(
                students.getContent(),
                students.getNumber(),
                students.getSize(),
                students.getTotalElements(),
                students.getTotalPages()
        );

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/student/{id}")
    @Operation(summary = "Get a student")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Student returned", content = @Content(schema = @Schema(implementation = StudentResponseDTO.class))),
            @ApiResponse(responseCode = "401", description = "Authentication required", content = @Content),
            @ApiResponse(responseCode = "404", description = "Student not found", content = @Content)
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AdminStudentResponseDTO> getStudent(@Parameter(description = "Student ID", required = true) @PathVariable UUID id) {
        AdminStudentResponseDTO studentResponseDTO = adminService.getStudentById(id);

        return ResponseEntity.ok().body(studentResponseDTO);
    }

    @GetMapping("/parent")
    @Operation(summary = "List parents", description = "Returns parents.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Parents returned"),
            @ApiResponse(responseCode = "401", description = "Authentication required", content = @Content),
            @ApiResponse(responseCode = "403", description = "User is not allowed to view parents", content = @Content)
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PageResponse<AdminParentResponseDTO>> getAllParents(
            @Parameter(description = "Search by first name or last name") @RequestParam(required = false) String search,
            @PageableDefault(size = 10, sort = "lastName", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<AdminParentResponseDTO> parents = adminService.getParents(search, pageable);
        PageResponse<AdminParentResponseDTO> response = new PageResponse<>(
                parents.getContent(),
                parents.getNumber(),
                parents.getSize(),
                parents.getTotalElements(),
                parents.getTotalPages()
        );
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/parent/{id}")
    @Operation(summary = "Get a parent")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Parent returned", content = @Content(schema = @Schema(implementation = ParentResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Parent not found", content = @Content)
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AdminParentResponseDTO> getParentById(@Parameter(description = "Parent ID", required = true) @PathVariable UUID id) {
        return ResponseEntity.ok().body(adminService.getParentById(id));
    }

    @GetMapping("/teacher")
    @Operation(summary = "Returns a paginated and sorted list of teachers.")
    @ApiResponse(responseCode = "200", description = "Teachers returned")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PageResponse<AdminTeacherResponseDTO>> getAllTeachers(
            @Parameter(description = "Search by first name or last name")
            @RequestParam(required = false) String search,
            @PageableDefault(size = 10, sort = "lastName", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<AdminTeacherResponseDTO> teachers = adminService.getTeachers(search, pageable);

        PageResponse<AdminTeacherResponseDTO> response = new PageResponse<>(
                teachers.getContent(),
                teachers.getNumber(),
                teachers.getSize(),
                teachers.getTotalElements(),
                teachers.getTotalPages()
        );

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/teacher/{id}")
    @Operation(summary = "Get a teacher")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Teacher returned", content = @Content(schema = @Schema(implementation = TeacherResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Teacher not found", content = @Content)
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AdminTeacherResponseDTO> getTeacherById(@Parameter(description = "Teacher ID", required = true) @PathVariable UUID id) {
        return ResponseEntity.ok(adminService.getTeacherById(id));
    }
}
