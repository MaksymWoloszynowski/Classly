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
    private UUID categoryId;
    private double grade;
    private GradeType type;
    private String description;
    private int weight;
    private LocalDate date;
    private String studentFullName;
    private String subject;
}