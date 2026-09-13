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
import org.edziennik.gradeservice.security.TeacherAccessService;
import org.edziennik.security.AuthenticatedUser;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GradeCategoryService {
    private final SchoolStructureGrpcClient schoolStructureClient;
    private final GradeCategoryRepository gradeCategoryRepository;
    private final TeacherAccessService teacherAccessService;

    public GradeCategoryService(GradeCategoryRepository gradeCategoryRepository, SchoolStructureGrpcClient schoolStructureClient, TeacherAccessService teacherAccessService) {
        this.gradeCategoryRepository = gradeCategoryRepository;
        this.schoolStructureClient = schoolStructureClient;
        this.teacherAccessService = teacherAccessService;
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
        teacherAccessService.requireAssignmentAccess(user, categoryRequestDTO.getTeachingAssignmentId());

        GradeCategory newGrade = gradeCategoryRepository.save(GradeCategoryMapper.toModel(categoryRequestDTO));

        return mapToDTO(newGrade);
    }

    public GradeCategoryResponseDTO updateGradeCategory(UUID gradeCategoryId, GradeCategoryRequestDTO gradeRequestDTO, AuthenticatedUser user) {
        GradeCategory gradeCategory = getGradeCategory(gradeCategoryId);

        teacherAccessService.requireAssignmentAccess(user, gradeCategory.getTeachingAssignmentId());

        gradeCategory.setDescription(gradeRequestDTO.getDescription());
        gradeCategory.setWeight(gradeRequestDTO.getWeight());
        gradeCategory.setType(gradeRequestDTO.getType());

        GradeCategory updated = gradeCategoryRepository.save(gradeCategory);

        return mapToDTO(updated);
    }

    public void deleteGradeCategory(UUID id, AuthenticatedUser user) {
        GradeCategory gradeCategory = getGradeCategory(id);

        teacherAccessService.requireAssignmentAccess(user, gradeCategory.getTeachingAssignmentId());

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