package org.edziennik.schoolstructureservice.teachingAssignment.service;

import org.edziennik.schoolstructureservice.group.entity.Group;
import org.edziennik.schoolstructureservice.group.exception.GroupNotFoundException;
import org.edziennik.schoolstructureservice.group.repository.GroupRepository;
import org.edziennik.schoolstructureservice.subject.entity.Subject;
import org.edziennik.schoolstructureservice.subject.exception.SubjectNotFoundException;
import org.edziennik.schoolstructureservice.subject.repository.SubjectRepository;
import org.edziennik.schoolstructureservice.teacher.entity.Teacher;
import org.edziennik.schoolstructureservice.teacher.exception.TeacherNotFoundException;
import org.edziennik.schoolstructureservice.teacher.repository.TeacherRepository;
import org.edziennik.schoolstructureservice.teachingAssignment.dto.TeachingAssignmentRequestDTO;
import org.edziennik.schoolstructureservice.teachingAssignment.dto.TeachingAssignmentResponseDTO;
import org.edziennik.schoolstructureservice.teachingAssignment.entity.TeachingAssignment;
import org.edziennik.schoolstructureservice.teachingAssignment.exception.TeachingAssignmentNotFoundException;
import org.edziennik.schoolstructureservice.teachingAssignment.mapper.TeachingAssignmentMapper;
import org.edziennik.schoolstructureservice.teachingAssignment.repository.TeachingAssignmentRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TeachingAssignmentService {
    private final TeachingAssignmentRepository teachingAssignmentRepository;
    private final TeacherRepository teacherRepository;
    private final SubjectRepository subjectRepository;
    private final GroupRepository groupRepository;

    public TeachingAssignmentService(TeachingAssignmentRepository teachingAssignmentRepository,
                                     TeacherRepository teacherRepository,
                                     SubjectRepository subjectRepository,
                                     GroupRepository groupRepository) {
        this.teachingAssignmentRepository = teachingAssignmentRepository;
        this.teacherRepository = teacherRepository;
        this.subjectRepository = subjectRepository;
        this.groupRepository = groupRepository;
    }

    public List<TeachingAssignmentResponseDTO> getAssignments(UUID groupId, UUID teacherId, UUID subjectId) {
        Specification<TeachingAssignment> spec = Specification.allOf();

        if (groupId != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("groupId"), groupId));
        }
        if (teacherId != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("teacherId"), teacherId));
        }
        if (subjectId != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("subjectId"), subjectId));
        }

        return teachingAssignmentRepository.findAll(spec).stream().map(TeachingAssignmentMapper::toDTO).toList();
    }

    public TeachingAssignmentResponseDTO getAssignmentById(UUID id) {
        return TeachingAssignmentMapper.toDTO(getAssignment(id));
    }

    public TeachingAssignmentResponseDTO createAssignment(TeachingAssignmentRequestDTO dto) {
        Teacher teacher = getTeacher(dto.getTeacherId());
        Subject subject = getSubject(dto.getSubjectId());
        Group group = getGroup(dto.getGroupId());

        TeachingAssignment saved = teachingAssignmentRepository.save(
                TeachingAssignmentMapper.toModel(teacher, subject, group));

        return TeachingAssignmentMapper.toDTO(saved);
    }

    public TeachingAssignmentResponseDTO updateAssignment(UUID id, TeachingAssignmentRequestDTO dto) {
        TeachingAssignment assignment = getAssignment(id);

        assignment.setTeacher(getTeacher(dto.getTeacherId()));
        assignment.setSubject(getSubject(dto.getSubjectId()));
        assignment.setGroup(getGroup(dto.getGroupId()));

        TeachingAssignment updated = teachingAssignmentRepository.save(assignment);
        return TeachingAssignmentMapper.toDTO(updated);
    }

    public void deleteAssignment(UUID id) {
        TeachingAssignment assignment = getAssignment(id);
        teachingAssignmentRepository.delete(assignment);
    }

    private TeachingAssignment getAssignment(UUID id) {
        return teachingAssignmentRepository.findById(id)
                .orElseThrow(() -> new TeachingAssignmentNotFoundException("Assignment not found with ID: " + id));
    }

    private Teacher getTeacher(UUID id) {
        return teacherRepository.findById(id)
                .orElseThrow(() -> new TeacherNotFoundException("Teacher not found with ID: " + id));
    }

    private Subject getSubject(UUID id) {
        return subjectRepository.findById(id)
                .orElseThrow(() -> new SubjectNotFoundException("Subject not found with ID: " + id));
    }

    private Group getGroup(UUID id) {
        return groupRepository.findById(id)
                .orElseThrow(() -> new GroupNotFoundException("Group not found with ID: " + id));
    }
}