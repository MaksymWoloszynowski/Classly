# schedule-service

## Overview

Owns the class schedule. A schedule consists of recurring rules (`Schedule`), per-date exceptions (`ScheduleOverride`), and one-off lessons (`AdditionalSchedule`).

## Endpoints

```text
GET    /schedule
GET    /schedule/{id}
GET    /schedule/student?groupId&from&to   # resolved schedule for a group and date range
GET    /schedule/teacher?teacherId&from&to  # resolved schedule for a teacher and date range
POST   /schedule
PUT    /schedule/{id}
DELETE /schedule/{id}

GET    /schedule-override
GET    /schedule-override/{id}
POST   /schedule-override
PUT    /schedule-override/{id}
DELETE /schedule-override/{id}

GET    /additional-schedule
GET    /additional-schedule/{id}
POST   /additional-schedule
PUT    /additional-schedule/{id}
DELETE /additional-schedule/{id}
```

Paths are relative to this service. Through the gateway they are available under `/api/...`; the gateway strips the `/api` prefix.

Interactive docs: `http://localhost:8083/docs/schedule/swagger-ui.html`

## Entities

| Entity | Description |
|---|---|
| `Schedule` | Recurring rule with a teaching assignment, day, time range, room, and validity dates |
| `ScheduleOverride` | Per-date cancellation, substitution, or room change |
| `AdditionalSchedule` | One-off lesson not tied to a recurring rule |

## Dependencies

- **gRPC calls:** `school-structure-service` for teaching assignments and teacher, subject, and group data.
- **Database:** PostgreSQL database `schedule-service-db`, migrated with Flyway.

## Configuration

```text
DB_HOST=localhost
DB_PORT=5432
DB_USER=schedule_service
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
