# auth-service

## Overview

Handles account creation, login, JWT issuing/refresh and access codes creation. Accounts are created with a one-time access code that binds the account to a role and a domain record (`refId`).

## Endpoints

Full interactive docs available at `http://localhost:8085/docs/auth/swagger-ui.html`

## Entities

| Entity | Description |
|---|---|
| `User` | Login credentials, role, and `refId` pointing to a domain record |
| `RefreshToken` | Hashed long-lived token used to issue access tokens |
| `AccessCode` | One-time code binding a registration to a role and `refId` |

## Access code creation

After creating a new student, parent or teacher the `school-structure-service` sends an event to the Kafka topic `users` which is then consumed by the `auth-service`. After that a new access code is saved to the database with an appropriate role and refId. For more details please refer to the `school-structure-service` [README.md](../school-structure-service/README.md) `Kafka and access codes` point.

## Dependencies

- **gRPC calls:** `school-structure-service` for access code user date.
- **Kafka:** consumes`users` topic.
- **Database:** PostgreSQL database `auth-service-db`, migrated with Flyway.

## Configuration

```text
DB_HOST=localhost       # Docker Compose sets this to auth-service-db
DB_PORT=5432
DB_USER=auth_service
DB_PASSWORD=             # provided through /run/secrets/DB_PASSWORD by Compose
SCHOOL_STRUCTURE_HOST=localhost
SCHOOL_STRUCTURE_GRPC_PORT=9090
JWT_SECRET=             # must match the gateway and common security library
```