package org.edziennik.schoolstructureservice.parent.service;

import org.edziennik.schoolstructureservice.parent.dto.ParentRequestDTO;
import org.edziennik.schoolstructureservice.parent.dto.ParentResponseDTO;
import org.edziennik.schoolstructureservice.parent.entity.Parent;
import org.edziennik.schoolstructureservice.parent.mapper.ParentMapper;
import org.edziennik.schoolstructureservice.parent.repository.ParentRepository;
import org.edziennik.schoolstructureservice.parent.exception.ParentNotFoundException;
import org.edziennik.schoolstructureservice.student.entity.Student;
import org.edziennik.schoolstructureservice.student.exception.StudentNotFoundException;
import org.edziennik.schoolstructureservice.student.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ParentService {
    private final ParentRepository parentRepository;
    private final StudentRepository studentRepository;

    public ParentService(ParentRepository parentRepository, StudentRepository studentRepository) {
        this.parentRepository = parentRepository;
        this.studentRepository = studentRepository;
    }

    public List<ParentResponseDTO> getAllParents() {
        return parentRepository.findAll().stream().map(ParentMapper::toDTO).collect(Collectors.toList());
    }

    public ParentResponseDTO getParentById(UUID parentId) {
        Parent parent = getParent(parentId);

        return ParentMapper.toDTO(parent);
    }

    public ParentResponseDTO createParent(ParentRequestDTO parentRequestDTO) {
        Parent newParent = parentRepository.save(ParentMapper.toModel(parentRequestDTO));

        return ParentMapper.toDTO(newParent);
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

    private Parent getParent(UUID parentId) {
        return parentRepository.findById(parentId).orElseThrow(() -> new ParentNotFoundException("Parent not found with ID: " + parentId));
    }

    private Student getStudent(UUID studentId) {
        return studentRepository.findById(studentId).orElseThrow(() -> new StudentNotFoundException("Student not found with ID: " + studentId));
    }
}
