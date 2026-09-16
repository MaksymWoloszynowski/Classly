package org.edziennik.gradeservice.gradeCategory.service;

import org.edziennik.gradeservice.grade.dto.GradeSummaryDTO;
import org.edziennik.gradeservice.grade.entity.Grade;
import org.edziennik.gradeservice.grade.mapper.GradeMapper;
import org.edziennik.gradeservice.gradeCategory.dto.GradeCategoryRequestDTO;
import org.edziennik.gradeservice.gradeCategory.dto.GradeCategoryResponseDTO;
import org.edziennik.gradeservice.gradeCategory.entity.GradeCategory;
import org.edziennik.gradeservice.gradeCategory.exception.GradeCategoryNotFoundException;
import org.edziennik.gradeservice.gradeCategory.mapper.GradeCategoryMapper;
import org.edziennik.gradeservice.gradeCategory.repository.GradeCategoryRepository;
import org.edziennik.gradeservice.grpc.SchoolStructureGrpcClient;
import org.edziennik.schoolstructureservice.grpc.StudentResponse;
import org.edziennik.security.AuthenticatedUser;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GradeCategoryService {
    private final SchoolStructureGrpcClient schoolStructureClient;
    private final GradeCategoryRepository gradeCategoryRepository;

    public GradeCategoryService(GradeCategoryRepository gradeCategoryRepository, SchoolStructureGrpcClient schoolStructureClient) {
        this.gradeCategoryRepository = gradeCategoryRepository;
        this.schoolStructureClient = schoolStructureClient;
    }

    public List<GradeCategoryResponseDTO> getGradeCategories(UUID teachingAssignmentId) {
        return getGradeCategories(teachingAssignmentId, null);
        }

        public List<GradeCategoryResponseDTO> getGradeCategories(UUID teachingAssignmentId, UUID classificationPeriod) {
        List<GradeCategory> categories = classificationPeriod == null
            ? gradeCategoryRepository.findByTeachingAssignmentId(teachingAssignmentId)
            : gradeCategoryRepository.findByTeachingAssignmentIdAndClassificationPeriod(
                teachingAssignmentId,
                classificationPeriod
            );

        return categories.stream()
            .map(this::mapToDTO)
            .toList();
    }

    public GradeCategoryResponseDTO getGradeCategoryById(UUID gradeId) {
        return mapToDTO(getGradeCategory(gradeId));
    }

    public GradeCategoryResponseDTO createGradeCategory(GradeCategoryRequestDTO categoryRequestDTO, AuthenticatedUser user) {
        GradeCategory newGrade = gradeCategoryRepository.save(GradeCategoryMapper.toModel(categoryRequestDTO));

        return mapToDTO(newGrade);
    }

    public GradeCategoryResponseDTO updateGradeCategory(UUID gradeCategoryId, GradeCategoryRequestDTO categoryRequestDTO, AuthenticatedUser user) {
        GradeCategory gradeCategory = getGradeCategory(gradeCategoryId);

        gradeCategory.setDescription(categoryRequestDTO.getDescription());
        gradeCategory.setWeight(categoryRequestDTO.getWeight());
        gradeCategory.setType(categoryRequestDTO.getType());

        GradeCategory updated = gradeCategoryRepository.save(gradeCategory);

        return mapToDTO(updated);
    }

    public void deleteGradeCategory(UUID id, AuthenticatedUser user) {
        GradeCategory gradeCategory = getGradeCategory(id);

        gradeCategoryRepository.delete(gradeCategory);
    }

    private GradeCategory getGradeCategory(UUID gradeCategoryId) {
        return gradeCategoryRepository.findById(gradeCategoryId)
                .orElseThrow(() -> new GradeCategoryNotFoundException("Grade category not found with ID: " + gradeCategoryId));
    }

    private GradeCategoryResponseDTO mapToDTO(GradeCategory gradeCategory) {
        GradeCategoryResponseDTO response = GradeCategoryMapper.toDTO(gradeCategory);
        List<Grade> categoryGrades = Optional.ofNullable(gradeCategory.getGrades())
            .orElseGet(Collections::emptyList);
        Set<UUID> studentIds = categoryGrades.stream()
                .map(Grade::getStudentId)
                .collect(java.util.stream.Collectors.toSet());
        Map<UUID, StudentResponse> students = schoolStructureClient.getStudents(studentIds);

        List<GradeSummaryDTO> grades = categoryGrades.stream()
                .map(GradeMapper::toSummaryDTO)
                .peek(grade -> {
                    StudentResponse student = students.get(grade.getStudentId());
                    if (student != null) {
                        grade.setStudentFullName(student.getFirstName() + " " + student.getLastName());
                    }
                })
                .toList();

        response.setGrades(grades);
        return response;
    }
}