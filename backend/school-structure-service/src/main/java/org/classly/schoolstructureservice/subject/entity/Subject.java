package org.classly.schoolstructureservice.subject.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.classly.schoolstructureservice.teachingAssignment.entity.TeachingAssignment;
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
@Table(name = "subjects")
public class Subject {
    @Id
    @UuidGenerator
    private UUID id;

    @NotBlank
    private String name;

    @OneToMany(mappedBy = "subject")
    private Set<TeachingAssignment> teachingAssignments = new HashSet<>();
}
