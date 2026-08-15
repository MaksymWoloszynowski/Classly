package org.edziennik.teachingservice.session.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class SessionRequestDTO {
    @NotNull
    private UUID teachingAssignmentId;

    private String description;
}
