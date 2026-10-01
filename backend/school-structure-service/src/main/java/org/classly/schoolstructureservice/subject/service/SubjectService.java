package org.classly.schoolstructureservice.subject.service;

import org.classly.schoolstructureservice.subject.dto.SubjectRequestDTO;
import org.classly.schoolstructureservice.subject.dto.SubjectResponseDTO;
import org.classly.schoolstructureservice.subject.entity.Subject;
import org.classly.schoolstructureservice.subject.exception.SubjectNotFoundException;
import org.classly.schoolstructureservice.subject.mapper.SubjectMapper;
import org.classly.schoolstructureservice.subject.repository.SubjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SubjectService {
    private final SubjectRepository subjectRepository;

    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    public List<SubjectResponseDTO> getAllSubjects() {
        return subjectRepository.findAll().stream().map(SubjectMapper::toDTO).collect(Collectors.toList());
    }

    public SubjectResponseDTO getSubjectById(UUID subjectId) {
        Subject subject = getSubject(subjectId);

        return SubjectMapper.toDTO(subject);
    }

    public SubjectResponseDTO createSubject(SubjectRequestDTO subjectRequestDTO) {
        Subject newSubject = subjectRepository.save(SubjectMapper.toModel(subjectRequestDTO));

        return SubjectMapper.toDTO(newSubject);
    }

    public SubjectResponseDTO updateSubject(UUID subjectId, SubjectRequestDTO subjectRequestDTO) {
        Subject subject = getSubject(subjectId);

        subject.setName(subjectRequestDTO.getName());

        Subject newSubject = subjectRepository.save(subject);

        return SubjectMapper.toDTO(newSubject);
    }

    public void deleteSubject(UUID subjectId) {
        Subject subject = getSubject(subjectId);
        subjectRepository.delete(subject);
    }

    private Subject getSubject(UUID subjectId) {
        return subjectRepository.findById(subjectId).orElseThrow(() -> new SubjectNotFoundException("Subject not found with ID: " + subjectId));
    }
}
