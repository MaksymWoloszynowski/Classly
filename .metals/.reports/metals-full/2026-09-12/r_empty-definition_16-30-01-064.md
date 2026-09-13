error id: file://<WORKSPACE>/backend/grade-service/src/main/java/org/edziennik/gradeservice/security/TeacherAccessService.java:_empty_/SchoolStructureGrpcClient#getTeachingAssignmentIdsByTeacher#
file://<WORKSPACE>/backend/grade-service/src/main/java/org/edziennik/gradeservice/security/TeacherAccessService.java
empty definition using pc, found symbol in pc: _empty_/SchoolStructureGrpcClient#getTeachingAssignmentIdsByTeacher#
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 750
uri: file://<WORKSPACE>/backend/grade-service/src/main/java/org/edziennik/gradeservice/security/TeacherAccessService.java
text:
```scala
package org.edziennik.gradeservice.security;

import org.edziennik.gradeservice.grpc.SchoolStructureGrpcClient;
import org.edziennik.security.AuthenticatedUser;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TeacherAccessService {
    private final SchoolStructureGrpcClient schoolStructureClient;

    public TeacherAccessService(SchoolStructureGrpcClient schoolStructureClient) {
        this.schoolStructureClient = schoolStructureClient;
    }

    public List<UUID> requireTeacher(AuthenticatedUser user) {
        if (user == null || !"ROLE_TEACHER".equals(user.role())) {
            throw new TeacherAccessDeniedException();
        }
        return schoolStructureClient.@@getTeachingAssignmentIdsByTeacher(user.refId());
    }

    public void requireAssignmentAccess(AuthenticatedUser user, UUID teachingAssignmentId) {
        if (!requireTeacher(user).contains(teachingAssignmentId)) {
            throw new TeacherAccessDeniedException();
        }
    }
}

```


#### Short summary: 

empty definition using pc, found symbol in pc: _empty_/SchoolStructureGrpcClient#getTeachingAssignmentIdsByTeacher#