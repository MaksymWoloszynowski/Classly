# teaching-service

## Overview

Owns what happens in a lesson: sessions, attendance, and assessments. A `Session` is one concrete occurrence linked to a schedule slot, and attendance records are linked to a session.

## Endpoints

```text
GET    /session?teachingAssignmentId&from&to
GET    /session/{id}
GET    /session/date?groupId&from&to
GET    /session/query/teacher?from&to
POST   /session
PUT    /session/{id}
DELETE /session/{id}

GET    /attendance?sessionId&studentId
GET    /attendance/date?studentId&from&to
POST   /attendance
DELETE /attendance/{id}

GET    /assessment?teachingAssignmentId
GET    /assessment/student/date?groupId&from&to
GET    /assessment/teacher/date?from&to
GET    /assessment/{id}
POST   /assessment
PUT    /assessment/{id}
DELETE /assessment/{id}
```

Paths are relative to this service. Through the gateway they are available under `/api/...`; the gateway strips the `/api` prefix.

Interactive docs: `http://localhost:8084/docs/teaching/swagger-ui.html`

## Entities

| Entity | Description |
|---|---|
| `Session` | One concrete lesson occurrence linked to a schedule slot |
| `Attendance` | A student's attendance status for a session |
| `Assessment` | An upcoming or past test or quiz for a teaching assignment |

## Dependencies

- **gRPC calls:** `school-structure-service` for school data and `schedule-service` for schedule slot details.
- **Database:** PostgreSQL database `teaching-service-db`, migrated with Flyway.

## Configuration

```text
DB_HOST=localhost
DB_PORT=5432
DB_USER=teaching_service
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
