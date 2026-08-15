package org.edziennik.schoolstructureservice.subject.dto;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubjectResponseDTO {
    private UUID id;
    private String name;
}
