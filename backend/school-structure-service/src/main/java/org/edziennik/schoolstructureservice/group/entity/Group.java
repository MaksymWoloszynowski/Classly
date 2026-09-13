package org.edziennik.schoolstructureservice.group.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.edziennik.schoolstructureservice.student.entity.Student;
import org.edziennik.schoolstructureservice.teacher.entity.Teacher;
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
@Table(name = "school_groups")
public class Group {
    @Id
    @UuidGenerator
    private UUID id;

    @NotBlank
    private String name;

    @OneToMany(mappedBy = "group")
    private Set<Student> students = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "homeroom_teacher_id")
    private Teacher homeroomTeacher;

    @OneToMany(mappedBy = "group")
    private Set<TeachingAssignment> teachingAssignments = new HashSet<>();
}