package org.classly.schoolstructureservice.classificationPeriod.mapper;

import org.classly.schoolstructureservice.classificationPeriod.dto.ClassificationPeriodRequestDTO;
import org.classly.schoolstructureservice.classificationPeriod.dto.ClassificationPeriodResponseDTO;
import org.classly.schoolstructureservice.classificationPeriod.entity.ClassificationPeriod;

public class ClassificationPeriodMapper {
    public static ClassificationPeriodResponseDTO toDTO(ClassificationPeriod period) {
        return ClassificationPeriodResponseDTO.builder()
                .id(period.getId())
                .dateFrom(period.getDateFrom())
                .dateTo(period.getDateTo())
                .semester(period.getSemester())
                .build();
    }

    public static ClassificationPeriod toModel(ClassificationPeriodRequestDTO requestDTO) {
        return ClassificationPeriod.builder()
                .dateFrom(requestDTO.getDateFrom())
                .dateTo(requestDTO.getDateTo())
                .semester(requestDTO.getSemester())
                .build();
    }
}
