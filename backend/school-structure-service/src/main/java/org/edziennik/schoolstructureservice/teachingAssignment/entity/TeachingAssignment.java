package org.edziennik.schoolstructureservice.teachingAssignment.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.edziennik.schoolstructureservice.group.entity.Group;
import org.edziennik.schoolstructureservice.subject.entity.Subject;
import org.edziennik.schoolstructureservice.teacher.entity.Teacher;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "teaching_assignments",
        uniqueConstraints = @UniqueConstraint(columnNames = {"subject_id", "group_id"})
)
public class TeachingAssignment {
    @Id
    @UuidGenerator
    private UUID id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;
}
