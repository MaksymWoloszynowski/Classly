package org.edziennik.schoolstructureservice.parent.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParentSummaryDTO {
    private UUID id;
    private String firstName;
    private String lastName;
}