package org.classly.schoolstructureservice.teacher.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.classly.schoolstructureservice.group.entity.Group;
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
@Table(name = "teachers")
public class Teacher {
    @Id
    @UuidGenerator
    private UUID id;

    @NotBlank
    private String socialId;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @OneToMany(mappedBy = "teacher")
    @Builder.Default
    private Set<TeachingAssignment> teachingAssignments = new HashSet<>();

    @OneToMany(mappedBy = "homeroomTeacher")
    @Builder.Default
    private Set<Group> groups = new HashSet<>();
}
