# teaching-service

## Overview

Owns what happens in a lesson: sessions, attendance, and assessments. A `Session` is one concrete occurrence linked to a schedule slot, and attendance records are linked to a session.

## Endpoints

Full interactive docs available at `http://localhost:8084/docs/teaching/swagger-ui.html`

## Entities

| Entity | Description |
|---|---|
| `Session` | One concrete lesson occurrence linked to a schedule slot |
| `Attendance` | A student's attendance status for a session |
| `Assessment` | An upcoming or past test or quiz for a teaching assignment |

## Dependencies

- **gRPC calls:** `school-structure-service` for school data.
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