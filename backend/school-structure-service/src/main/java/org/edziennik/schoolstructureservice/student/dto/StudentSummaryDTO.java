package org.edziennik.schoolstructureservice.student.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentSummaryDTO {
    private UUID id;
    private String firstName;
    private String lastName;
}