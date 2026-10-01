package org.classly.gradeservice.grade.service;

import org.classly.gradeservice.grade.dto.GradeRequestDTO;
import org.classly.gradeservice.grade.dto.GradeResponseDTO;
import org.classly.gradeservice.grade.dto.SubjectGradeResponseDTO;
import org.classly.gradeservice.grade.exception.GradeNotFoundException;
import org.classly.gradeservice.grade.mapper.GradeMapper;
import org.classly.gradeservice.grade.entity.Grade;
import org.classly.gradeservice.grade.repository.GradeRepository;
import org.classly.gradeservice.gradeCategory.exception.GradeCategoryNotFoundException;
import org.classly.gradeservice.gradeCategory.entity.GradeCategory;
import org.classly.gradeservice.gradeCategory.repository.GradeCategoryRepository;
import org.classly.gradeservice.grpc.SchoolStructureGrpcClient;
import org.classly.schoolstructureservice.grpc.*;
import org.classly.security.AuthenticatedUser;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GradeService {
    private final SchoolStructureGrpcClient schoolStructureClient;
    private final GradeRepository gradeRepository;
    private final GradeCategoryRepository gradeCategoryRepository;

    public GradeService(GradeRepository gradeRepository, SchoolStructureGrpcClient schoolStructureClient, GradeCategoryRepository gradeCategoryRepository) {
        this.gradeRepository = gradeRepository;
        this.schoolStructureClient = schoolStructureClient;
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
            spec = spec.and((root, query, cb) -> cb.equal(
                    root.get("gradeCategory").get("teachingAssignmentId"),
                    teachingAssignmentId
            ));
        }
        if (classificationPeriod != null) {
            spec = spec.and((root, query, cb) -> cb.equal(
                    root.get("gradeCategory").get("classificationPeriod"),
                    classificationPeriod
            ));
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

        Grade newGrade = gradeRepository.save(GradeMapper.toModel(gradeRequestDTO, gradeCategory));

        return mapToDTO(newGrade);
    }

    public GradeResponseDTO updateGrade(UUID gradeId, GradeRequestDTO gradeRequestDTO, AuthenticatedUser user) {
        Grade grade = getGrade(gradeId);

        grade.setGrade(gradeRequestDTO.getGrade());
        Grade updated = gradeRepository.save(grade);

        return mapToDTO(updated);
    }

    public void deleteGrade(UUID id, AuthenticatedUser user) {
        Grade grade = getGrade(id);

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