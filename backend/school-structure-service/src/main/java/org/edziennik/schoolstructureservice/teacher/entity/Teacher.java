package org.edziennik.schoolstructureservice.teacher.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.edziennik.schoolstructureservice.teachingAssignment.entity.TeachingAssignment;
import org.hibernate.annotations.UuidGenerator;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "teachers")
public class Teacher {
    @Id
    @UuidGenerator
    private UUID id;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @OneToMany(mappedBy = "teacher")
    private Set<TeachingAssignment> teachingAssignments = new HashSet<>();
}
