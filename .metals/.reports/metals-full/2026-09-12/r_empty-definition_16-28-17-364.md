error id: file://<WORKSPACE>/backend/grade-service/src/main/java/org/edziennik/gradeservice/gradeCategory/service/GradeCategoryService.java:org/edziennik/security/AuthenticatedUser#
file://<WORKSPACE>/backend/grade-service/src/main/java/org/edziennik/gradeservice/gradeCategory/service/GradeCategoryService.java
empty definition using pc, found symbol in pc: org/edziennik/security/AuthenticatedUser#
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 959
uri: file://<WORKSPACE>/backend/grade-service/src/main/java/org/edziennik/gradeservice/gradeCategory/service/GradeCategoryService.java
text:
```scala
package org.edziennik.gradeservice.gradeCategory.service;

import org.edziennik.gradeservice.grade.entity.Grade;
import org.edziennik.gradeservice.grade.exception.GradeNotFoundException;
import org.edziennik.gradeservice.gradeCategory.dto.GradeCategoryRequestDTO;
import org.edziennik.gradeservice.gradeCategory.dto.GradeCategoryResponseDTO;
import org.edziennik.gradeservice.gradeCategory.entity.GradeCategory;
import org.edziennik.gradeservice.gradeCategory.exception.GradeNotFoundException;
import org.edziennik.gradeservice.gradeCategory.mapper.GradeCategoryMapper;
import org.edziennik.gradeservice.gradeCategory.mapper.GradeMapper;
import org.edziennik.gradeservice.gradeCategory.repository.GradeCategoryRepository;
import org.edziennik.gradeservice.gradeCategory.repository.GradeRepository;
import org.edziennik.gradeservice.grpc.SchoolStructureGrpcClient;
import org.edziennik.gradeservice.security.TeacherAccessService;
import org.edziennik.security.@@AuthenticatedUser;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
                .orElseThrow(() -> new GradeNotFoundException("Grade category not found with ID: " + gradeCategoryId));
    }
}
```


#### Short summary: 

empty definition using pc, found symbol in pc: org/edziennik/security/AuthenticatedUser#