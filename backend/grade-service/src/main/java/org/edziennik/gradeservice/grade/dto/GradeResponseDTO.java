package org.edziennik.gradeservice.grade.dto;

import lombok.*;
import org.edziennik.gradeservice.grade.entity.GradeType;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GradeResponseDTO  {
    private UUID id;
    private double grade;
    private GradeType type;
    private String description;
    private int semester;
    private int weight;
    private LocalDate date;
    private UUID studentId;
    private String studentFullName;
    private UUID subjectId;
    private String subjectName;
}