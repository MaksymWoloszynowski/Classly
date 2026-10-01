# api-gateway

## Overview

The single public entry point for the backend. It routes HTTP requests to the appropriate service, validates the JWT from the `access_token` cookie, and forwards the authenticated user information downstream.

The gateway listens on port `8080` by default.

## Routes

| Gateway path | Downstream service | Prefix removed |
|---|---|---|
| `/auth/**` | auth-service (`8085`) | no |
| `/api/group/**`, `/api/student/**`, `/api/teacher/**`, `/api/subject/**`, `/api/parent/**`, `/api/teaching-assignment/**`, `/api/my-profile/**`, `/api/classification-period/**` | school-structure-service (`8081`) | `/api` |
| `/api/grade/**`, `/api/semester-grade/**`, `/api/grade-category/**` | grade-service (`8082`) | `/api` |
| `/api/schedule/**`, `/api/schedule-override/**` | schedule-service (`8083`) | `/api` |
| `/api/assessment/**`, `/api/attendance/**`, `/api/session/**` | teaching-service (`8084`) | `/api` |

Example URLs through the gateway:

```text
http://localhost:8080/auth/login
http://localhost:8080/api/student
```

## Responsibilities

- Verifies the JWT signature and expiry on protected requests.
- Rejects missing or invalid tokens with `401`.
- Applies credentials-aware CORS for the frontend.

## Configuration

```text
JWT_SECRET=  # must match auth-service and the common security library
```