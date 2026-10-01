# grade-service

## Overview

Owns grades. Every grade belongs to a `ClassificationPeriod`, which allows the grading calendar to be configured explicitly.

## Endpoints

```text
GET    /grade                              # optional: studentId, groupId, teachingAssignmentId, classificationPeriod
GET    /grade/{id}
GET    /grade/date                         # required: studentId, from, to
POST   /grade
PUT    /grade/{id}
DELETE /grade/{id}

GET    /grade-category                     # optional: teachingAssignmentId, classificationPeriod
GET    /grade-category/{id}
POST   /grade-category
PUT    /grade-category/{id}
DELETE /grade-category/{id}

GET    /semester-grade                     # optional: studentId, groupId, subjectId, classificationPeriod
GET    /semester-grade/{id}
POST   /semester-grade
PUT    /semester-grade/{id}
DELETE /semester-grade/{id}
```

Interactive docs: `http://localhost:8082/docs/grade/swagger-ui.html`

## Entities

| Entity | Description |
|---|---|
| `Grade` | A grade linked to a student, subject, and `ClassificationPeriod` |
| `GradeCategory` | Category used to organize and authorize grades for a teaching assignment |
| `SemesterGrade` | A semester-level grade linked to a student, subject, and `ClassificationPeriod` |

## Dependencies

- **gRPC calls:** `school-structure-service` for student, subject, group, and teaching-assignment data.
- **Database:** PostgreSQL database `grade-service-db`, migrated with Flyway.

## Configuration

```text
DB_HOST=localhost
DB_PORT=5432
DB_USER=grade_service
DB_PASSWORD=             # provided through /run/secrets/DB_PASSWORD by Compose
SCHOOL_STRUCTURE_HOST=localhost
SCHOOL_STRUCTURE_GRPC_PORT=9090
JWT_SECRET=             # shared with auth-service and the gateway
```

## Running standalone

```bash
set -a; source .env; set +a
./mvnw spring-boot:run
```
