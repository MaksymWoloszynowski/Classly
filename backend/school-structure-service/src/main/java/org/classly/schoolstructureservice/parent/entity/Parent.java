package org.classly.schoolstructureservice.parent.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.classly.schoolstructureservice.student.entity.Student;
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
@Table(name = "parents")
public class Parent {
    @Id
    @UuidGenerator
    private UUID id;

    @NotBlank
    private String socialId;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @ManyToMany
    @JoinTable(
            name = "parents_students",
            joinColumns = @JoinColumn(name = "parent_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private Set<Student> students = new HashSet<>();
}
