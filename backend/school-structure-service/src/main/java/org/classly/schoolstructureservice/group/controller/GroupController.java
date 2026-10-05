package org.classly.schoolstructureservice.group.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.classly.schoolstructureservice.group.dto.GroupRequestDTO;
import org.classly.schoolstructureservice.group.dto.GroupResponseDTO;
import org.classly.schoolstructureservice.group.dto.GroupSummaryDTO;
import org.classly.schoolstructureservice.group.service.GroupService;
import org.classly.schoolstructureservice.student.dto.StudentResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.net.URI;

@RestController
@RequestMapping("/group")
@Tag(name = "Groups", description = "Manage groups")
@SecurityRequirement(name = "cookieAuth")
public class GroupController {
    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping
    @Operation(summary = "List groups", description = "Returns groups.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Groups returned"),
            @ApiResponse(responseCode = "401", description = "Authentication required", content = @Content),
            @ApiResponse(responseCode = "403", description = "User is not allowed to view groups", content = @Content)
    })
    public ResponseEntity<List<GroupResponseDTO>> getAllGroups() {
        return ResponseEntity.ok().body(groupService.getAllGroups());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a group")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Group returned", content = @Content(schema = @Schema(implementation = GroupResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Group not found", content = @Content)
    })
    public ResponseEntity<GroupResponseDTO> getGroupById(@Parameter(description = "Group ID", required = true) @PathVariable UUID id) {
        return ResponseEntity.ok().body(groupService.getGroupById(id));
    }

    @GetMapping("/summary")
    @Operation(summary = "List groups", description = "Returns only group names.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Groups returned"),
            @ApiResponse(responseCode = "401", description = "Authentication required", content = @Content),
            @ApiResponse(responseCode = "403", description = "User is not allowed to view groups", content = @Content)
    })
    public ResponseEntity<List<GroupSummaryDTO>> getAllGroupsSummary() {
        return ResponseEntity.ok().body(groupService.getAllGroupsSummary());
    }

    @GetMapping("/{id}/students")
    @Operation(summary = "List students in a group")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Students returned"),
        @ApiResponse(responseCode = "404", description = "Group not found", content = @Content)
    })
    public ResponseEntity<Set<StudentResponseDTO>> getStudentsFromGroup(@Parameter(description = "Group ID", required = true) @PathVariable UUID id) {
        return ResponseEntity.ok().body(groupService.getStudentsFromGroup(id));
    }

    @PostMapping
    @Operation(summary = "Create a group")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Group created", content = @Content(schema = @Schema(implementation = GroupResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Validation failed", content = @Content),
        @ApiResponse(responseCode = "403", description = "Admin role required", content = @Content)
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GroupResponseDTO> createGroup(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Group to create", required = true) @Valid @RequestBody GroupRequestDTO groupRequestDTO) {
        GroupResponseDTO createdGroup = groupService.createGroup(groupRequestDTO);
        return ResponseEntity.created(URI.create("/group/" + createdGroup.getId())).body(createdGroup);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a group")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Group updated", content = @Content(schema = @Schema(implementation = GroupResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Validation failed", content = @Content),
        @ApiResponse(responseCode = "403", description = "Admin role required", content = @Content),
        @ApiResponse(responseCode = "404", description = "Group not found", content = @Content)
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GroupResponseDTO> updateGroup(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated group data", required = true) @Valid @RequestBody GroupRequestDTO groupRequestDTO, @Parameter(description = "Group ID", required = true) @PathVariable UUID id) {
        return ResponseEntity.ok().body(groupService.updateGroup(id, groupRequestDTO));
    }

    @PutMapping("/{groupId}/homeroom-teacher/{teacherId}")
    @Operation(summary = "Assign a homeroom teacher")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Homeroom teacher assigned", content = @Content(schema = @Schema(implementation = GroupResponseDTO.class))),
        @ApiResponse(responseCode = "403", description = "Admin role required", content = @Content),
        @ApiResponse(responseCode = "404", description = "Group or teacher not found", content = @Content)
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GroupResponseDTO> updateHomeroomTeacher(@Parameter(description = "Group ID", required = true) @PathVariable UUID groupId, @Parameter(description = "Teacher ID", required = true) @PathVariable UUID teacherId) {
        return ResponseEntity.ok().body(groupService.updateHomeroomTeacher(groupId, teacherId));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a group")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Group deleted"),
        @ApiResponse(responseCode = "404", description = "Group not found", content = @Content)
    })
    public ResponseEntity<Void> deleteGroup(@Parameter(description = "Group ID", required = true) @PathVariable UUID id) {
        groupService.deleteGroup(id);

        return ResponseEntity.noContent().build();
    }

}