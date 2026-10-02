# school-structure-service

## Overview

Owns the core school structure: students, teachers, parents, groups, subjects, teaching assignments, and classification periods. Other services use this service through gRPC to resolve names and related identifiers.

## Endpoints

Full interactive docs available at `http://localhost:8081/docs/school-structure/swagger-ui.html`

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

## Kafka and access codes

When an administrator creates a new student, parent, or teacher through the
`POST /student`, `POST /parent`, or `POST /teacher` endpoint, this service:

1. Saves the new person and obtains their UUID.
2. Publishes a protobuf `UserCreatedEvent` to the Kafka topic `users`.
3. Uses the person's UUID as the Kafka message key and includes the UUID and
   role (`ROLE_STUDENT`, `ROLE_PARENT`, or `ROLE_TEACHER`) in the event.

The auth-service consumes the `users` topic. For each event it creates a
one-time access code associated with the event role and `refId` (the person's
UUID).

Administrators can manage generated codes through the auth-service endpoints.

## Dependencies

- **Called via gRPC by:** `schedule-service`, `grade-service`, and `teaching-service`.
- **gRPC server:** listens on port `9090`.
- **Kafka:** publishes `UserCreatedEvent` messages to the `users` topic; auth-service consumes them to create access codes.
- **Database:** PostgreSQL database `school-structure-service-db`, migrated with Flyway.

## Configuration

```text
DB_HOST=localhost
DB_PORT=5432
DB_USER=school_structure_service
DB_PASSWORD=             # provided through /run/secrets/DB_PASSWORD by Compose
JWT_SECRET=             # shared with auth-service and the gateway
```
