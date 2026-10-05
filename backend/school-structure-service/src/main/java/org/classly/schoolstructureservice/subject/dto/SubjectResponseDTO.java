package org.classly.schoolstructureservice.subject.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Individual subject returned by the service")
public class SubjectResponseDTO {
    @Schema(description = "Subject ID")
    private UUID id;

    @Schema(description = "Subject name")
    private String name;
}
