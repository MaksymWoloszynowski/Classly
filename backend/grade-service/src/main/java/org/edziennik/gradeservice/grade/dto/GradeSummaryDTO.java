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
public class GradeSummaryDTO {
    private UUID id;
    private double grade;
    private LocalDate date;
    private UUID studentId;
    private String studentFullName;
}
