# grade-service

## Overview

Owns grades. Every grade belongs to a `ClassificationPeriod`, which allows the grading calendar to be configured explicitly and a `GradeCategory` to orginize them.

## Endpoints

Full interactive docs available at `http://localhost:8082/docs/grade/swagger-ui.html`

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