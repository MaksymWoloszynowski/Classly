package org.classly.schoolstructureservice.grpc;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import org.classly.schoolstructureservice.parent.entity.Parent;
import org.classly.schoolstructureservice.parent.repository.ParentRepository;
import org.classly.schoolstructureservice.student.entity.Student;
import org.classly.schoolstructureservice.student.repository.StudentRepository;
import org.classly.schoolstructureservice.subject.repository.SubjectRepository;
import org.classly.schoolstructureservice.teacher.entity.Teacher;
import org.classly.schoolstructureservice.teacher.repository.TeacherRepository;
import org.classly.schoolstructureservice.teachingAssignment.entity.TeachingAssignment;
import org.classly.schoolstructureservice.teachingAssignment.repository.TeachingAssignmentRepository;
import org.springframework.grpc.server.service.GrpcService;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@GrpcService
@AllArgsConstructor
public class SchoolStructureGrpcService extends SchoolStructureServiceGrpc.SchoolStructureServiceImplBase {
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;
    private final TeachingAssignmentRepository teachingAssignmentRepository;
    private final TeacherRepository teacherRepository;
    private final ParentRepository parentRepository;

    @Override
    public void getStudent(StudentRequest request, StreamObserver<StudentResponse> responseObserver) {
        UUID studentId = UUID.fromString(request.getStudentId());

        studentRepository.findById(studentId).ifPresentOrElse(
                student -> {
                    StudentResponse response = toStudentResponse(student);
                    responseObserver.onNext(response);
                    responseObserver.onCompleted();
                },
                () -> responseObserver.onError(
                        Status.NOT_FOUND
                                .withDescription("Student not found with id: " + studentId)
                                .asRuntimeException()
                )
        );
    }

    @Override
    public void getStudents(StudentIdsRequest request, StreamObserver<StudentListResponse> responseObserver) {
        Set<UUID> studentIds = request.getStudentIdsList().stream().map(UUID::fromString).collect(Collectors.toSet());

        List<Student> students = studentRepository.findByIdIn(studentIds);

        StudentListResponse response = StudentListResponse.newBuilder()
                .addAllStudents(
                        students
                                .stream()
                                .map(this::toStudentResponse)
                                .toList()
                ).build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getSubject(SubjectRequest request, StreamObserver<SubjectResponse> responseObserver) {
        UUID subjectId = UUID.fromString(request.getSubjectId());

        subjectRepository.findById(subjectId).ifPresentOrElse(
                subject -> {
                    SubjectResponse response = SubjectResponse.newBuilder()
                            .setName(subject.getName())
                            .build();
                    responseObserver.onNext(response);
                    responseObserver.onCompleted();
                },
                () -> responseObserver.onError(
                        Status.NOT_FOUND
                                .withDescription("Subject not found with id: " + subjectId)
                                .asRuntimeException()
                )
        );
    }

    @Override
    public void getTeacher(TeacherRequest request, StreamObserver<TeacherResponse> responseObserver) {
        UUID teacherId = UUID.fromString(request.getTeacherId());

        teacherRepository.findById(teacherId).ifPresentOrElse(
                teacher -> {
                    TeacherResponse response = toTeacherResponse(teacher);
                    responseObserver.onNext(response);
                    responseObserver.onCompleted();
                },
                () -> responseObserver.onError(
                        Status.NOT_FOUND
                                .withDescription("Teacher not found with id: " + teacherId)
                                .asRuntimeException()
                )
        );
    }

    @Override
    public void getTeachers(TeacherIdsRequest request, StreamObserver<TeacherListResponse> responseObserver) {
        Set<UUID> teacherIds = request.getTeacherIdsList().stream().map(UUID::fromString).collect(Collectors.toSet());

        List<Teacher> teachers = teacherRepository.findByIdIn(teacherIds);

        TeacherListResponse response = TeacherListResponse.newBuilder()
                .addAllTeacher(
                        teachers
                                .stream()
                                .map(this::toTeacherResponse)
                                .toList()
                ).build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getParents(ParentIdsRequest request, StreamObserver<ParentListResponse> responseObserver) {
        Set<UUID> parentIds = request.getParentIdsList().stream().map(UUID::fromString).collect(Collectors.toSet());

        List<Parent> parents = parentRepository.findByIdIn(parentIds);

        ParentListResponse response = ParentListResponse.newBuilder()
                .addAllParents(
                        parents
                                .stream()
                                .map(this::toParentResponse)
                                .toList()
                ).build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getTeachingAssignment(TeachingAssignmentRequest request, StreamObserver<TeachingAssignmentResponse> responseObserver) {
        UUID teachingAssignmentId = UUID.fromString(request.getTeachingAssignmentId());

        teachingAssignmentRepository.findById(teachingAssignmentId).ifPresentOrElse(
                teachingAssignment -> {
                    TeachingAssignmentResponse response = toTeachingAssignmentResponse(teachingAssignment);
                    responseObserver.onNext(response);
                    responseObserver.onCompleted();
                },
                () -> responseObserver.onError(
                        Status.NOT_FOUND
                                .withDescription("Teaching assignment not found with id: " + teachingAssignmentId)
                                .asRuntimeException()
                )
        );
    }

    @Override

    public void getTeachingAssignments(TeachingAssignmentIdsRequest request, StreamObserver<TeachingAssignmentListResponse> responseObserver) {

        Set<UUID> assignmentIds = request.getTeachingAssignmentIdsList()
                .stream()
                .map(UUID::fromString)
                .collect(Collectors.toSet());

        List<TeachingAssignment> assignments = teachingAssignmentRepository.findAllByIdIn(assignmentIds);

        TeachingAssignmentListResponse response = TeachingAssignmentListResponse.newBuilder()
                        .addAllTeachingAssignments(
                                assignments.stream()
                                        .map(this::toTeachingAssignmentResponse)
                                        .toList()
                        )
                        .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getStudentIdsByGroup(GroupRequest request, StreamObserver<StudentIdListResponse> responseObserver) {
        UUID groupId = UUID.fromString(request.getGroupId());

        List<Student> students = studentRepository.findByGroupId(groupId);

        StudentIdListResponse response = StudentIdListResponse.newBuilder().addAllStudentIds(students.stream().map(student -> student.getId().toString()).toList()).build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getTeachingAssignmentIdsByGroup(GroupRequest request, StreamObserver<TeachingAssignmentIdListResponse> responseObserver) {
        UUID groupId = UUID.fromString(request.getGroupId());

        List<TeachingAssignment> assignments = teachingAssignmentRepository.findByGroupId(groupId);

        TeachingAssignmentIdListResponse response = TeachingAssignmentIdListResponse.newBuilder().addAllTeachingAssignmentIds(assignments.stream().map(assignment -> assignment.getId().toString()).toList()).build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getTeachingAssignmentIdsByTeacher(TeacherRequest request, StreamObserver<TeachingAssignmentIdListResponse> responseObserver) {
        UUID teacherId = UUID.fromString(request.getTeacherId());

        List<TeachingAssignment> assignments= teachingAssignmentRepository.findByTeacherId(teacherId);

        TeachingAssignmentIdListResponse response = TeachingAssignmentIdListResponse.newBuilder().addAllTeachingAssignmentIds(assignments.stream().map(assignment -> assignment.getId().toString()).toList()).build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    private StudentResponse toStudentResponse(Student student) {
        StudentResponse.Builder builder = StudentResponse.newBuilder()
                .setId(student.getId().toString())
                .setFirstName(student.getFirstName())
                .setLastName(student.getLastName());

        if (student.getGroup() != null) {
            builder.setGroupId(student.getGroup().getId().toString());
        }

        return builder.build();
    }

    private ParentResponse toParentResponse(Parent parent) {
        return ParentResponse.newBuilder()
                .setId(parent.getId().toString())
                .setName(parent.getFirstName()+" "+parent.getLastName())
                .build();
    }

    private TeacherResponse toTeacherResponse(Teacher teacher) {
        return TeacherResponse.newBuilder()
                .setId(teacher.getId().toString())
                .setName(teacher.getFirstName()+" "+teacher.getLastName())
                .build();
    }

    private TeachingAssignmentResponse toTeachingAssignmentResponse(TeachingAssignment assignment) {
        return TeachingAssignmentResponse.newBuilder()
                .setId(assignment.getId().toString())
                .setGroup(assignment.getGroup().getName())
                .setTeacher(assignment.getTeacher().getFirstName()+ " " + assignment.getTeacher().getLastName())
                .setSubject(assignment.getSubject().getName())
                .build();
    }
}
