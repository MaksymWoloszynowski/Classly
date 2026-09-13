error id: file://<WORKSPACE>/backend/grade-service/src/main/java/org/edziennik/gradeservice/grade/service/GradeService.java:org/edziennik/gradeservice/security/TeacherAccessService#
file://<WORKSPACE>/backend/grade-service/src/main/java/org/edziennik/gradeservice/grade/service/GradeService.java
empty definition using pc, found symbol in pc: org/edziennik/gradeservice/security/TeacherAccessService#
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 852
uri: file://<WORKSPACE>/backend/grade-service/src/main/java/org/edziennik/gradeservice/grade/service/GradeService.java
text:
```scala
package org.edziennik.gradeservice.grade.service;

import org.edziennik.gradeservice.grade.dto.GradeRequestDTO;
import org.edziennik.gradeservice.grade.dto.GradeResponseDTO;
import org.edziennik.gradeservice.grade.dto.SubjectGradeResponseDTO;
import org.edziennik.gradeservice.grade.exception.GradeNotFoundException;
import org.edziennik.gradeservice.grade.mapper.GradeMapper;
import org.edziennik.gradeservice.grade.entity.Grade;
import org.edziennik.gradeservice.grade.repository.GradeRepository;
import org.edziennik.gradeservice.gradeCategory.exception.GradeCategoryNotFoundException;
import org.edziennik.gradeservice.gradeCategory.entity.GradeCategory;
import org.edziennik.gradeservice.gradeCategory.repository.GradeCategoryRepository;
import org.edziennik.gradeservice.grpc.SchoolStructureGrpcClient;
import org.edziennik.gradeservice.security.@@TeacherAccessService;
import org.edziennik.schoolstructureservice.grpc.*;
import org.edziennik.security.AuthenticatedUser;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GradeService {
    private final SchoolStructureGrpcClient schoolStructureClient;
    private final GradeRepository gradeRepository;
    private final TeacherAccessService teacherAccessService;
    private final GradeCategoryRepository gradeCategoryRepository;

    public GradeService(GradeRepository gradeRepository, SchoolStructureGrpcClient schoolStructureClient, TeacherAccessService teacherAccessService, GradeCategoryRepository gradeCategoryRepository) {
        this.gradeRepository = gradeRepository;
        this.schoolStructureClient = schoolStructureClient;
        this.teacherAccessService = teacherAccessService;
        this.gradeCategoryRepository = gradeCategoryRepository;
    }

    public Map<UUID, Map<UUID, SubjectGradeResponseDTO>> getGrades(UUID studentId, UUID groupId, UUID teachingAssignmentId, UUID classificationPeriod) {
        Specification<Grade> spec = Specification.allOf();

        if (studentId != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("studentId"), studentId));
        }
        if (groupId != null) {
            List<UUID> studentIds = schoolStructureClient.getStudentIdsForGroup(groupId);
            spec = spec.and((root, query, cb) -> root.get("studentId").in(studentIds));
        }
        if (teachingAssignmentId != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("teachingAssignmentId"), teachingAssignmentId));
        }
        if (classificationPeriod != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("classificationPeriod"), classificationPeriod));
        }

        return mapToDTOList(gradeRepository.findAll(spec));
    }

    public GradeResponseDTO getGradeById(UUID gradeId) {
        return mapToDTO(getGrade(gradeId));
    }

    public Map<UUID, List<GradeResponseDTO>> getGradesByDate(UUID studentId, LocalDate from, LocalDate to) {
        List<Grade> grades = gradeRepository.findByStudentIdAndDateBetween(studentId, from, to);

        return mapToDTOLatest(grades);
    }

    public GradeResponseDTO createGrade(GradeRequestDTO gradeRequestDTO, AuthenticatedUser user) {
        GradeCategory gradeCategory = getGradeCategoryFromRequestDTO(gradeRequestDTO);
        UUID teachingAssignmentId = gradeCategory.getTeachingAssignmentId();

        teacherAccessService.requireAssignmentAccess(user, teachingAssignmentId);

        Grade newGrade = gradeRepository.save(GradeMapper.toModel(gradeRequestDTO, gradeCategory));

        return mapToDTO(newGrade);
    }

    public GradeResponseDTO updateGrade(UUID gradeId, GradeRequestDTO gradeRequestDTO, AuthenticatedUser user) {
        Grade grade = getGrade(gradeId);
        teacherAccessService.requireAssignmentAccess(user, grade.getGradeCategory().getTeachingAssignmentId());

        grade.setGrade(gradeRequestDTO.getGrade());
        Grade updated = gradeRepository.save(grade);

        return mapToDTO(updated);
    }

    public void deleteGrade(UUID id, AuthenticatedUser user) {
        Grade grade = getGrade(id);
        teacherAccessService.requireAssignmentAccess(user, grade.getGradeCategory().getTeachingAssignmentId());

        gradeRepository.delete(grade);
    }

    private GradeResponseDTO mapToDTO(Grade grade) {
        StudentResponse studentResponse = schoolStructureClient.getStudent(grade.getStudentId());
        TeachingAssignmentResponse teachingAssignmentResponse = schoolStructureClient.getTeachingAssignment(grade.getGradeCategory().getTeachingAssignmentId());

        GradeResponseDTO responseDTO = GradeMapper.toDTO(grade);

        responseDTO.setSubject(teachingAssignmentResponse.getSubject());
        responseDTO.setStudentFullName(studentResponse.getFirstName() + " " + studentResponse.getLastName());

        return responseDTO;
    }

    private Map<UUID, Map<UUID, SubjectGradeResponseDTO>> mapToDTOList(List<Grade> grades) {
        Set<UUID> studentIds = extractStudentIds(grades);
        Set<UUID> teachingAssignmentIds = extractTeachingAssignmentIds(grades);

        Map<UUID, StudentResponse> students =
                schoolStructureClient.getStudents(studentIds);

        Map<UUID, TeachingAssignmentResponse> teachingAssignments =
                schoolStructureClient.getTeachingAssignments(teachingAssignmentIds);

        return groupGrades(grades, students, teachingAssignments);
    }

    private Map<UUID, List<GradeResponseDTO>> mapToDTOLatest(List<Grade> grades) {
        return grades.stream()
                .collect(Collectors.groupingBy(
                        grade -> grade.getGradeCategory().getTeachingAssignmentId(),
                        Collectors.mapping(
                                this::mapToDTO,
                                Collectors.toList()
                        )
                ));
    }

    private Map<UUID, Map<UUID, SubjectGradeResponseDTO>> groupGrades(
            List<Grade> grades,
            Map<UUID, StudentResponse> students,
            Map<UUID, TeachingAssignmentResponse> teachingAssignments
    ) {
        return grades.stream()
                .collect(Collectors.groupingBy(
                        grade -> grade.getGradeCategory().getTeachingAssignmentId(),
                        Collectors.groupingBy(
                                Grade::getStudentId,
                                Collectors.collectingAndThen(
                                        Collectors.toList(),
                                        studentGrades -> buildSubjectGradeResponse(
                                                studentGrades,
                                                students,
                                                teachingAssignments
                                        )
                                )
                        )
                ));
    }

    private List<GradeResponseDTO> mapGradesToDTO(List<Grade> grades, Map<UUID, StudentResponse> studentNames, Map<UUID, TeachingAssignmentResponse> teachingAssignmentResponseMap) {
        return grades.stream().map(grade -> {
                    GradeResponseDTO dto = GradeMapper.toDTO(grade);
                    dto.setStudentFullName(studentNames.get(grade.getStudentId()).getFirstName() + " " + studentNames.get(grade.getStudentId()).getLastName());
                    dto.setSubject(teachingAssignmentResponseMap.get(grade.getGradeCategory().getTeachingAssignmentId()).getSubject());
                    return dto;
                })
                .sorted(Comparator.comparing(GradeResponseDTO::getDate).reversed())
                .collect(Collectors.toList());
    }

    private Set<UUID> extractStudentIds(List<Grade> grades) {
        return grades.stream()
                .map(Grade::getStudentId)
                .collect(Collectors.toSet());
    }

    private Set<UUID> extractTeachingAssignmentIds(List<Grade> grades) {
        return grades.stream()
                .map(grade -> grade.getGradeCategory().getTeachingAssignmentId())
                .collect(Collectors.toSet());
    }

    private double calculateAverage(List<Grade> grades) {
        double weightedSum = grades.stream()
                .mapToDouble(grade -> grade.getGrade() * grade.getGradeCategory().getWeight())
                .sum();

        double weightSum = grades.stream()
                .mapToDouble(grade -> grade.getGradeCategory().getWeight())
                .sum();

        return weightSum == 0 ? 0.0 : weightedSum / weightSum;
    }

    private Grade getGrade(UUID gradeId) {
        return gradeRepository.findById(gradeId)
                .orElseThrow(() -> new GradeNotFoundException("Grade not found with ID: " + gradeId));
    }

    private GradeCategory getGradeCategoryFromRequestDTO(GradeRequestDTO requestDTO) {
        UUID gradeCategoryId = requestDTO.getGradeCategoryId();

        return gradeCategoryRepository.findById(gradeCategoryId)
                .orElseThrow(() -> new GradeCategoryNotFoundException("Grade category not found with ID: " + gradeCategoryId))
                ;
    }

    private SubjectGradeResponseDTO buildSubjectGradeResponse(
            List<Grade> studentGrades,
            Map<UUID, StudentResponse> students,
            Map<UUID, TeachingAssignmentResponse> teachingAssignments
    ) {
        List<GradeResponseDTO> gradeDTOs = mapGradesToDTO(
                studentGrades,
                students,
                teachingAssignments
        );

        double average = calculateAverage(studentGrades);

        return SubjectGradeResponseDTO.builder()
                .grades(gradeDTOs)
                .average(average)
                .build();
    }
}
```


#### Short summary: 

empty definition using pc, found symbol in pc: org/edziennik/gradeservice/security/TeacherAccessService#