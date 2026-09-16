package org.edziennik.schoolstructureservice.group.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.edziennik.schoolstructureservice.group.dto.GroupRequestDTO;
import org.edziennik.schoolstructureservice.group.dto.GroupResponseDTO;
import org.edziennik.schoolstructureservice.group.service.GroupService;
import org.edziennik.schoolstructureservice.student.dto.StudentResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

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
    public ResponseEntity<GroupResponseDTO> getGroupById(@PathVariable UUID id) {
        return ResponseEntity.ok().body(groupService.getGroupById(id));
    }

    @GetMapping("/{id}/students")
    public ResponseEntity<Set<StudentResponseDTO>> getStudentsFromGroup(@PathVariable UUID id) {
        return ResponseEntity.ok().body(groupService.getStudentsFromGroup(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GroupResponseDTO> createGroup(@Valid @RequestBody GroupRequestDTO groupRequestDTO) {
        return ResponseEntity.ok().body(groupService.createGroup(groupRequestDTO));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GroupResponseDTO> updateGroup(@Valid @RequestBody GroupRequestDTO groupRequestDTO, @PathVariable UUID id) {
        return ResponseEntity.ok().body(groupService.updateGroup(id, groupRequestDTO));
    }

    @PutMapping("/{groupId}/homeroom-teacher/{teacherId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GroupResponseDTO> updateHomeroomTeacher(@PathVariable UUID groupId, @PathVariable UUID teacherId) {
        return ResponseEntity.ok().body(groupService.updateHomeroomTeacher(groupId, teacherId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable UUID id) {
        groupService.deleteGroup(id);

        return ResponseEntity.noContent().build();
    }

}