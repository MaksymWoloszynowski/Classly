# school-structure-service

## Overview

Owns the core school structure: students, teachers, parents, groups, subjects, teaching assignments, and classification periods. Other services use this service through gRPC to resolve names and related identifiers.

## Endpoints

```text
GET    /student?groupId
GET    /student/{id}
POST   /student
PUT    /student/{id}
DELETE /student/{id}
POST   /student/{studentId}/group/{groupId}
DELETE /student/{id}/group

GET    /teacher
GET    /teacher/{id}
POST   /teacher
PUT    /teacher/{id}
DELETE /teacher/{id}

GET    /parent
GET    /parent/{id}
POST   /parent
PUT    /parent/{id}
DELETE /parent/{id}
POST   /parent/{parentId}/student/{studentId}
DELETE /parent/{parentId}/student/{studentId}

GET    /group
GET    /group/{id}
GET    /group/{id}/students
POST   /group
PUT    /group/{id}
PUT    /group/{groupId}/homeroom-teacher/{teacherId}
DELETE /group/{id}

GET    /subject
GET    /subject/{id}
POST   /subject
PUT    /subject/{id}
DELETE /subject/{id}

GET    /teaching-assignment?groupId&teacherId&subjectId
GET    /teaching-assignment/{id}
POST   /teaching-assignment
PUT    /teaching-assignment/{id}
DELETE /teaching-assignment/{id}

GET    /classification-period
GET    /classification-period/{id}
POST   /classification-period
PUT    /classification-period/{id}
DELETE /classification-period/{id}

GET    /my-profile
```

Paths are relative to this service. Through the gateway they are available under `/api/...`; the gateway strips the `/api` prefix.

Interactive docs: `http://localhost:8081/docs/school-structure/swagger-ui.html`

## Entities

| Entity | Description |
|---|---|
| `Student` | Student belonging to a school group |
| `Teacher` | Teacher profile |
| `Parent` | Parent linked to one or more students |
| `SchoolGroup` | Class with an optional homeroom teacher |
| `Subject` | Subject taught at the school |
| `TeachingAssignment` | Link between a teacher, subject, and group |
| `ClassificationPeriod` | Configurable period used by grading |

## Dependencies

- **Called via gRPC by:** `schedule-service`, `grade-service`, and `teaching-service`.
- **gRPC server:** listens on port `9090`.
- **Database:** PostgreSQL database `school-structure-service-db`, migrated with Flyway.

## Configuration

```text
DB_HOST=localhost
DB_PORT=5432
DB_USER=school_structure_service
DB_PASSWORD=             # provided through /run/secrets/DB_PASSWORD by Compose
JWT_SECRET=             # shared with auth-service and the gateway
```

## Running standalone

```bash
set -a; source .env; set +a
./mvnw spring-boot:run
```
