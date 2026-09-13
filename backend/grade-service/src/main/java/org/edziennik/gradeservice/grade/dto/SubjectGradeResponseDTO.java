package org.edziennik.gradeservice.grade.dto;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubjectGradeResponseDTO {
    private List<GradeResponseDTO> grades;
    private double average;
}
