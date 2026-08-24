package org.edziennik.schoolstructureservice.classificationPeriod.service;

import org.edziennik.schoolstructureservice.classificationPeriod.dto.ClassificationPeriodRequestDTO;
import org.edziennik.schoolstructureservice.classificationPeriod.dto.ClassificationPeriodResponseDTO;
import org.edziennik.schoolstructureservice.classificationPeriod.entity.ClassificationPeriod;
import org.edziennik.schoolstructureservice.classificationPeriod.exception.ClassificationPeriodNotFoundException;
import org.edziennik.schoolstructureservice.classificationPeriod.mapper.ClassificationPeriodMapper;
import org.edziennik.schoolstructureservice.classificationPeriod.repository.ClassificationPeriodRepository;
import org.edziennik.schoolstructureservice.student.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ClassificationPeriodService {
    private final ClassificationPeriodRepository classificationPeriodRepository;

    public ClassificationPeriodService(ClassificationPeriodRepository classificationPeriodRepository) {
        this.classificationPeriodRepository = classificationPeriodRepository;
    }

    public List<ClassificationPeriodResponseDTO> getAllClassificationPeriods() {
        return classificationPeriodRepository.findAll().stream().map(ClassificationPeriodMapper::toDTO).collect(Collectors.toList());
    }

    public ClassificationPeriodResponseDTO getClassificationPeriodById(UUID classificationPeriodId) {
        ClassificationPeriod classificationPeriod = getClassificationPeriod(classificationPeriodId);

        return ClassificationPeriodMapper.toDTO(classificationPeriod);
    }

    public ClassificationPeriodResponseDTO createClassificationPeriod(ClassificationPeriodRequestDTO classificationPeriodRequestDTO) {
        ClassificationPeriod newClassificationPeriod = classificationPeriodRepository.save(ClassificationPeriodMapper.toModel(classificationPeriodRequestDTO));

        return ClassificationPeriodMapper.toDTO(newClassificationPeriod);
    }


    public ClassificationPeriodResponseDTO updateClassificationPeriod(UUID classificationPeriodId, ClassificationPeriodRequestDTO classificationPeriodRequestDTO) {
        ClassificationPeriod classificationPeriod = getClassificationPeriod(classificationPeriodId);

        classificationPeriod.setDateFrom(classificationPeriodRequestDTO.getDateFrom());
        classificationPeriod.setDateTo(classificationPeriodRequestDTO.getDateTo());

        return ClassificationPeriodMapper.toDTO(classificationPeriod);
    }

    public void deleteClassificationPeriod(UUID classificationPeriodId) {
        ClassificationPeriod classificationPeriod = getClassificationPeriod(classificationPeriodId);
        classificationPeriodRepository.delete(classificationPeriod);
    }

    private ClassificationPeriod getClassificationPeriod(UUID classificationPeriodId) {
        return classificationPeriodRepository.findById(classificationPeriodId).orElseThrow(() -> new ClassificationPeriodNotFoundException("ClassificationPeriod not found with ID: " + classificationPeriodId));
    }
}
