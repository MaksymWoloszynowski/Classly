package org.classly.schoolstructureservice.student.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.classly.schoolstructureservice.group.entity.Group;
import org.classly.schoolstructureservice.parent.entity.Parent;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "students")
public class Student {
    @Id
    @UuidGenerator
    private UUID id;

    @NotBlank
    private String socialId;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotNull
    private LocalDate dateOfBirth;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    @Builder.Default
    @ManyToMany(mappedBy = "students")
    private Set<Parent> parents = new HashSet<>();
}
