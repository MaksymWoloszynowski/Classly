# common

## Overview

Shared Maven library. It provides JWT verification and Spring Security wiring so domain services can reuse the same authentication filter.

## What it provides

| Class | Purpose |
|---|---|
| `AuthenticatedUser` | Record holding `userId`, `role`, and `refId` |
| `JwtVerifier` | Verifies JWT signature and expiry and extracts `AuthenticatedUser` claims |
| `JwtAuthenticationFilter` | Reads the httpOnly cookie and populates the `SecurityContext` |
| `SecurityConfig` | Auto-configures the `SecurityFilterChain` |

The configuration is auto-registered through `META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports`.

## Required configuration

```yaml
jwt:
  secret: ${JWT_SECRET:...}   # must match auth-service and api-gateway
```

## Installing locally

From the repository root:

```bash
cd backend/common
mvn clean install
```

The current project version is inherited as `0.0.1-SNAPSHOT` from `backend/pom.xml`:

```xml
<dependency>
    <groupId>org.classly</groupId>
    <artifactId>common</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```