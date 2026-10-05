# Classly - School Management System

## Project Overview

Classly is a full-stack school management application built on a **Spring Boot Microservices** architecture. It handles grades, class schedules, attendance, assessments, and the core school structure (students, teachers, parents, groups, subjects).

### Architecture Components

- **API Gateway** - the only public entry point; routing and JWT verification
- **Auth Service** - login, access-code registration, JWT issuing/refresh
- **School Structure Service** - managing students, teachers, parents, groups, subjects, teaching assignments
- **Schedule Service** - recurring schedule rules, per-date overrides, one-off additional lessons
- **Grade Service** - grades, semester grades
- **Teaching Service** - lesson sessions, attendance, assessments
- **common** - shared library for JWT verification and authorization
- **PostgreSQL** - one database instance per service

```mermaid
flowchart LR
  Browser[Browser] -->|HTTP :80| Frontend[Frontend + Nginx]

  subgraph Public[Public network]
    Frontend
  end

  Frontend -->|/api, /auth, /docs| Gateway[API Gateway :8080]

  subgraph Internal[Internal Docker network]
    Gateway --> Auth[Auth Service :8085]
    Gateway --> School[School Structure :8081]
    Gateway --> Schedule[Schedule Service :8083]
    Gateway --> Grade[Grade Service :8082]
    Gateway --> Teaching[Teaching Service :8084]

    Schedule -.gRPC :9090.-> School
    Grade -.gRPC :9090.-> School
    Teaching -.gRPC :9090.-> School
    Auth -.gRPC :9090.-> School

    Auth --> AuthDB[(Auth PostgreSQL)]
    School --> SchoolDB[(School Structure PostgreSQL)]
    Schedule --> ScheduleDB[(Schedule PostgreSQL)]
    Grade --> GradeDB[(Grade PostgreSQL)]
    Teaching --> TeachingDB[(Teaching PostgreSQL)]
    School -.Kafka: users.-> Kafka[(Kafka)]
    Kafka -.UserCreatedEvent.-> Auth
  end
```

---

## Technologies

[![Java](https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1.0-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)](https://spring.io/)
[![Spring Security](https://img.shields.io/badge/Spring%20Security-7.1.0-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white)](https://spring.io/projects/spring-security)
[![Spring Cloud Gateway](https://img.shields.io/badge/Spring%20Cloud%20Gateway-MVC-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)](https://spring.io/projects/spring-cloud-gateway)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)](https://www.postgresql.org/)
[![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)](https://www.docker.com/)
[![gRPC](https://img.shields.io/badge/gRPC-4285F4?style=for-the-badge&logo=googlecloud&logoColor=white)](https://grpc.io/)
[![Apache Kafka](https://img.shields.io/badge/Apache%20Kafka-3.7.0-231F20?style=for-the-badge&logo=apachekafka&logoColor=white)](https://kafka.apache.org/)
[![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)](https://maven.apache.org/)
[![OpenAPI](https://img.shields.io/badge/OpenAPI-6BA539?style=for-the-badge&logo=openapiinitiative&logoColor=white)](https://springdoc.org/)
[![JWT](https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=jsonwebtokens&logoColor=white)](https://jwt.io/)
[![Flyway](https://img.shields.io/badge/Flyway-CC0200?style=for-the-badge&logo=flyway&logoColor=white)](https://flywaydb.org/)
[![Lombok](https://img.shields.io/badge/Lombok-BC4521?style=for-the-badge&logo=lombok&logoColor=white)](https://projectlombok.org/)

---

## Quick Start

### Prerequisites

- **Java 17+**
- **Maven 3.9+**
- **Docker & Docker Compose**
- **Git**

### Installation

```bash
# Clone the repository
git clone https://github.com/MaksymWoloszynowski/Classly.git
cd Classly
```

For the full Docker Compose stack, create the root environment file:

```bash
cp .env.example .env
```

Set `JWT_SECRET` and all database credentials to real values. The root Compose file
uses these variables for the services and Docker secrets. The per-service
`.env.example` files are intended for standalone service Compose runs.

### Running with Docker

```bash
docker compose up -d --build
```

The frontend will be available at `http://localhost`. Backend service ports are not published to the host.

---

## API Endpoints

All browser requests go through the frontend at `http://localhost`, which proxies them to the internal API Gateway.

### Public Endpoints

```bash
POST   /auth/login           
POST   /auth/register
POST   /auth/refresh
POST   /auth/logout
```

### Protected Endpoints examples (require a valid access token)

```bash
GET    /api/student/{id}
GET    /api/schedule/student?groupId&from&to
GET    /api/grade?studentId=...
GET    /api/attendance?sessionId=...
POST   /api/assessment
```

Full endpoint documentation is available per service via Swagger UI (see below).

---

## Configuration

### Service Ports

```yaml
Frontend:                  80 (published; container listens on 3000)
API Gateway:               8080 (internal)
School Structure Service:  8081 (internal)
Grade Service:             8082 (internal)
Schedule Service:          8083 (internal)
Teaching Service:          8084 (internal)
Auth Service:              8085 (internal)
Kafka:                     9092 (internal)
```

### Database

Each service owns its own PostgreSQL database and applies its own Flyway migrations
on startup. Database credentials are supplied to the root Compose stack as Docker
secrets named `DB_USER` and `DB_PASSWORD`.

### Kafka

Kafka runs as a single broker in the Docker Compose stack and is available to
services at `kafka:9092`. The school-structure-service publishes protobuf
`UserCreatedEvent` messages to the `users` topic whenever an administrator
creates a new student, parent, or teacher.

The event contains:

- the newly created person's UUID,
- the role (`ROLE_STUDENT`, `ROLE_PARENT`, or `ROLE_TEACHER`),
- the UUID as the Kafka message key.

The auth-service consumes the `users` topic and asynchronously creates a
one-time access code linked to the person's role and UUID (`refId`). Generated
codes contain 15 randomly selected characters from digits and upper- and
lower-case letters.

Administrators can manage generated codes through:

```text
GET    /auth/access-code?role=STUDENT&used=false
GET    /auth/access-code/{id}
GET    /auth/access-code/ref/{refId}
POST   /auth/access-code/
DELETE /auth/access-code/{id}
```

These endpoints are available through the gateway at
`http://localhost/auth/access-code...` and require the `ADMIN` role.


---

## Testing

### Registration 

The development database contains one-time access codes for the seeded test
records:

| Role | Access code |
| -------- | ------- |
| Student | 1111 |
| Parent | 2222 |
| Teacher | 3333 |
| Admin | 4444 |

These development codes are inserted by the auth-service test-data migration and
are one-time-use codes. Codes generated for newly created people are created by
the Kafka flow described above.

### Example API Calls

```bash
# 1. Register using an access code
curl -X POST http://localhost/auth/register \
  -H "Content-Type: application/json" \
  -d '{"email": "student@example.com", "password": "Password123", "accessCode": "1111"}'

# 2. Log in
curl -i -c cookies.txt -X POST http://localhost/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email": "student@example.com", "password": "Password123"}'

# 3. Call a protected endpoint
curl -X GET http://localhost/api/student \
  -b cookies.txt
```

### API Documentation (Swagger)

OpenAPI documentation is routed through the frontend without publishing service ports:

```
http://localhost/docs/school-structure/swagger-ui.html
http://localhost/docs/grade/swagger-ui.html
http://localhost/docs/schedule/swagger-ui.html
http://localhost/docs/teaching/swagger-ui.html
http://localhost/docs/auth/swagger-ui.html
```

---

## Project Structure

```
Classly/
│
├── backend
│   ├── api-gateway/           
│   ├── auth-service/              
│   ├── school-structure-service/  
│   ├── schedule-service/           
│   ├── grade-service/              
│   ├── teaching-service/           
│   └── common/   
│
├── frontend
│   └── src/
│       ├── api/
│       ├── components/
│       ├── context/
│       ├── hooks/
│       ├── i18n/
│       ├── layouts/
│       ├── pages/
│       ├── styles/
│       ├── types/
│       └── utils/
│
├── docker-compose.yaml
└── README.md
```

Each service has its own `README.md` with service-specific endpoints, entities,
configuration, and dependencies. The frontend has a separate README with its
local development instructions.

---