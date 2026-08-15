package org.edziennik.schoolstructureservice.group.controller;

import jakarta.validation.Valid;
import org.edziennik.schoolstructureservice.group.dto.GroupRequestDTO;
import org.edziennik.schoolstructureservice.group.dto.GroupResponseDTO;
import org.edziennik.schoolstructureservice.group.service.GroupService;
import org.edziennik.schoolstructureservice.student.dto.StudentResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/group")
public class GroupController {
    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping
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
    public ResponseEntity<GroupResponseDTO> createGroup(@Valid @RequestBody GroupRequestDTO groupRequestDTO) {
        return ResponseEntity.ok().body(groupService.createGroup(groupRequestDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GroupResponseDTO> updateGroup(@Valid @RequestBody GroupRequestDTO groupRequestDTO, @PathVariable UUID id) {
        return ResponseEntity.ok().body(groupService.updateGroup(id, groupRequestDTO));
    }

    @PutMapping("/{groupId}/homeroom-teacher/{teacherId}")
    public ResponseEntity<GroupResponseDTO> updateHomeroomTeacher(@PathVariable UUID groupId, @PathVariable UUID teacherId) {
        return ResponseEntity.ok().body(groupService.updateHomeroomTeacher(groupId, teacherId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable UUID id) {
        groupService.deleteGroup(id);

        return ResponseEntity.noContent().build();
    }

}