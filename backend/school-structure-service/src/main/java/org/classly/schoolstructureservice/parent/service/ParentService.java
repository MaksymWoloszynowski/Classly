package org.classly.schoolstructureservice.parent.service;

import org.classly.events.UserCreatedEvent;
import org.classly.schoolstructureservice.kafka.UserEventProducer;
import org.classly.schoolstructureservice.parent.dto.AdminParentResponseDTO;
import org.classly.schoolstructureservice.parent.dto.ParentRequestDTO;
import org.classly.schoolstructureservice.parent.dto.ParentResponseDTO;
import org.classly.schoolstructureservice.parent.entity.Parent;
import org.classly.schoolstructureservice.parent.mapper.ParentMapper;
import org.classly.schoolstructureservice.parent.repository.ParentRepository;
import org.classly.schoolstructureservice.parent.exception.ParentNotFoundException;
import org.classly.schoolstructureservice.student.entity.Student;
import org.classly.schoolstructureservice.student.exception.StudentNotFoundException;
import org.classly.schoolstructureservice.student.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

@Service
public class ParentService {
    private final ParentRepository parentRepository;
    private final StudentRepository studentRepository;
    private final UserEventProducer producer;

    public ParentService(ParentRepository parentRepository, StudentRepository studentRepository, UserEventProducer producer) {
        this.parentRepository = parentRepository;
        this.studentRepository = studentRepository;
        this.producer = producer;
    }

    public Page<ParentResponseDTO> getAllParents(String search, Pageable pageable) {
        Page<Parent> parents = getParents(search, pageable);
        return parents.map(ParentMapper::toDTO);
    }

    public Page<AdminParentResponseDTO> getAllParentsAdmin(String search, Pageable pageable) {
        Page<Parent> parents = getParents(search, pageable);
        return parents.map(ParentMapper::toAdminDTO);
    }

    public ParentResponseDTO getParentById(UUID parentId) {
        Parent parent = getParent(parentId);

        return ParentMapper.toDTO(parent);
    }

    public AdminParentResponseDTO getParentByIdAdmin(UUID parentId) {
        Parent parent = getParent(parentId);

        return ParentMapper.toAdminDTO(parent);
    }

    public ParentResponseDTO createParent(ParentRequestDTO parentRequestDTO) {
        Parent parent = parentRepository.save(ParentMapper.toModel(parentRequestDTO));
        UserCreatedEvent event = UserCreatedEvent.newBuilder()
                .setId(parent.getId().toString())
                .setRole("ROLE_PARENT")
                .build();
        producer.send(event);
        return ParentMapper.toDTO(parent);
    }

    @Transactional
    public ParentResponseDTO addStudentToParent(UUID parentId, UUID studentId) {
        Parent parent = getParent(parentId);
        Student student = getStudent(studentId);

        parent.getStudents().add(student);

        return ParentMapper.toDTO(parent);
    }

    @Transactional
    public ParentResponseDTO updateParent(UUID parentId, ParentRequestDTO parentRequestDTO) {
        Parent parent = getParent(parentId);

        parent.setFirstName(parentRequestDTO.getFirstName());
        parent.setLastName(parentRequestDTO.getLastName());
        parent.setSocialId(parentRequestDTO.getSocialId());

        return ParentMapper.toDTO(parent);
    }

    public void deleteParent(UUID parentId) {
        Parent parent = getParent(parentId);
        parentRepository.delete(parent);
    }

    @Transactional
    public ParentResponseDTO deleteStudentFromParent(UUID parentId, UUID studentId) {
        Parent parent = getParent(parentId);
        Student student = getStudent(studentId);

        parent.getStudents().remove(student);

        return ParentMapper.toDTO(parent);
    }

    private Page<Parent> getParents(String search, Pageable pageable) {
        return search == null || search.isBlank()
                ? parentRepository.findAllBy(pageable)
                : parentRepository.search(search.trim(), pageable);
    }

    private Parent getParent(UUID parentId) {
        return parentRepository.findById(parentId).orElseThrow(() -> new ParentNotFoundException("Parent not found with ID: " + parentId));
    }

    private Student getStudent(UUID studentId) {
        return studentRepository.findById(studentId).orElseThrow(() -> new StudentNotFoundException("Student not found with ID: " + studentId));
    }
}
