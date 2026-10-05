package org.classly.teachingservice.attendance.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.classly.teachingservice.session.entity.Session;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Attendance {
    @Id
    @UuidGenerator
    private UUID id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "session_id")
    private Session session;

    @NotNull
    private UUID studentId;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AttendanceType type;
}