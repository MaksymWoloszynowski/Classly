# auth-service

## Overview

Handles account creation, login, and JWT issuing/refresh. Accounts are created with a one-time access code that binds the account to a role and a domain record (`refId`).

## Endpoints

The service listens on port `8085` and uses the `/auth` controller prefix:

```text
POST   /auth/login       # issues access_token and refresh_token as httpOnly cookies
POST   /auth/register    # registers an account using an access code
POST   /auth/refresh     # issues a new access token from a refresh token
POST   /auth/logout      # revokes the refresh token and clears cookies
```

Through the gateway the same paths are available at `http://localhost:8080/auth/...`.

Interactive docs: `http://localhost:8085/docs/auth/swagger-ui.html`

## Entities

| Entity | Description |
|---|---|
| `User` | Login credentials, role, and `refId` pointing to a domain record |
| `RefreshToken` | Hashed long-lived token used to issue access tokens |
| `AccessCode` | One-time code binding a registration to a role and `refId` |

## Configuration

```text
DB_HOST=localhost       # Docker Compose sets this to auth-service-db
DB_PORT=5432
DB_USER=auth_service
DB_PASSWORD=             # provided through /run/secrets/DB_PASSWORD by Compose
JWT_SECRET=             # must match the gateway and common security library
```