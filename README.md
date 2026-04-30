# JobPilot AI Backend

Spring Boot backend for tracking job applications, notes, reminders, dashboard stats, and local AI-assisted career features.

## Tech Stack

- Java 21
- Spring Boot 3.5
- Spring Security + JWT
- Spring Data JPA + MySQL
- Spring AI + Ollama (qwen2.5:3b — runs locally, no API credits required)
- Maven
- 
## Architecture Overview

- Follows layered architecture:
  - Controller → Service → Repository → Database
- Spring Security with JWT for stateless authentication
- Spring Data JPA for ORM and database operations
- MySQL as the primary relational database
- Local AI integration using Ollama (no external API dependency)
- DTO-based request/response handling for clean API design
- Centralized exception handling using @ControllerAdvice

## Features

- Secure user authentication using JWT (signup/login)
- Full job application lifecycle management (create, update, delete, filter, pagination)
- Track notes and reminders for each application
- Reminder completion tracking for follow-ups
- Dashboard analytics with application status insights
- Clean layered architecture (Controller → Service → Repository → DB)
- Global exception handling with meaningful API responses

### AI-Powered Features (Local LLM via Ollama)

- Resume bullet improvement (rewrites using local LLM)
- Job Description analysis (extracts skills, keywords, and match insights)
- Interview question generation (role-specific)
- AI interaction history tracking per user

## Project Structure

```text
src/main/java/com/yourpackage/jobpilotai
│
├── controller
│
├── dto
│   ├── request
│   │   ├── LoginRequest.java
│   │   ├── ApplicationRequest.java
│   │   ├── NoteRequest.java
│   │   ├── ReminderRequest.java
│   │   ├── ResumeImproveRequest.java
│   │   ├── JobDescriptionRequest.java
│   │   ├── InterviewQuestionRequest.java
│   │
│   ├── response
│   │   ├── AuthResponse.java
│   │   ├── ApplicationResponse.java
│   │   ├── DashboardResponse.java
│   │   ├── AiResponse.java
│
├── entity
│   ├── User.java
│   ├── Application.java
│   ├── Note.java
│   ├── Reminder.java
│   ├── AiHistory.java
│   ├── enums
│   │   ├── ApplicationStatus.java
│   │   ├── SourceType.java
│   │   ├── Role.java
│   │   ├── AiFeatureType.java
│
├── exception
│   ├── GlobalExceptionHandler.java
│   ├── ResourceNotFoundException.java
│   ├── UnauthorizedException.java
│   ├── DuplicateResourceException.java
│
├── repository
│   ├── UserRepository.java
│   ├── ApplicationRepository.java
│   ├── NoteRepository.java
│   ├── ReminderRepository.java
│   ├── AiHistoryRepository.java
│
├── service
│   ├── AuthService.java
│   ├── ApplicationService.java
│   ├── DashboardService.java
│   ├── NoteService.java
│   ├── ReminderService.java
│   ├── AiService.java
│
├── service/impl
│   ├── AuthServiceImpl.java
│   ├── ApplicationServiceImpl.java
│   ├── DashboardServiceImpl.java
│   ├── NoteServiceImpl.java
│   ├── ReminderServiceImpl.java
│   ├── AiServiceImpl.java
│
├── util
│   ├── MapperUtil.java
│   ├── DateUtil.java
│
└── JobPilotAiApplication.java

## Prerequisites

- Java 21+
- Maven (or use included `./mvnw`)
- MySQL running locally
- Ollama installed locally

## Local Setup

1. Clone and open project

```bash
git clone <https://github.com/Vedangmodi/JobPilot-AI>
cd jobpilot-ai
```

2. Create MySQL database

```sql
CREATE DATABASE jobpilot_ai;
```

3. Configure `src/main/resources/application.properties`

Current keys used by this project:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/jobpilot_ai
spring.datasource.username=root
spring.datasource.password=your_password

jwt.secret=your_base64_secret
jwt.expiration=86400000

spring.ai.ollama.base-url=http://localhost:11434
spring.ai.ollama.chat.model=qwen2.5:3b
```

4. Start Ollama and pull model

```bash
ollama serve
ollama pull qwen2.5:3b
```

5. Run app

```bash
./mvnw spring-boot:run
```

App runs on `http://localhost:8080`

## Authentication Flow

1. `POST /api/auth/signup`
2. `POST /api/auth/login` -> copy `token` from response
3. For protected endpoints, send:

```http
Authorization: Bearer <your_token>
```

Only `/api/auth/**` is public. Everything else requires JWT.

## API Endpoints

### Auth

- `POST /api/auth/signup`
- `POST /api/auth/login`

### Applications

- `POST /api/applications`
- `GET /api/applications`
- `GET /api/applications/{id}`
- `PUT /api/applications/{id}`
- `DELETE /api/applications/{id}`
- `GET /api/applications/filter?applicationStatus=APPLIED&search=acme`
- `GET /api/applications/pagination?page=0&size=10`

### Notes

- `POST /api/applications/{applicationid}/notes`
- `GET /api/applications/{applicationid}/notes`
- `DELETE /api/notes/{noteId}`

### Reminders

- `POST /api/applications/{id}/reminders`
- `GET /api/applications/{id}/reminders`
- `PUT /api/reminders/{reminderId}/complete`
- `DELETE /api/reminders/{reminderId}`

### Dashboard

- `GET /api/dashboard/stats`

### AI

- `POST /api/ai/resume-improver`
- `POST /api/ai/jd-analyzer`
- `POST /api/ai/interview-questions`
- `GET /api/ai/history`

## Sample Request Bodies

### Signup

```json
{
  "name": "Vedang",
  "email": "vedang@example.com",
  "password": "secret123"
}
```

### Login

```json
{
  "email": "vedang@example.com",
  "password": "secret123"
}
```

### Create Application

```json
{
  "companyName": "Acme",
  "roleTitle": "Backend Intern",
  "jobLink": "https://example.com/job",
  "location": "Remote",
  "salary": 100000,
  "status": "APPLIED",
  "source": "LINKEDIN",
  "notesSummary": "Applied via referral",
  "appliedDate": "2026-05-01",
  "jobDescription": "Spring Boot, MySQL, REST APIs"
}
```

### Create Note

```json
{
  "content": "Need to revise system design before interview."
}
```

### Create Reminder

```json
{
  "message": "Follow up with recruiter",
  "reminderDate": "2026-05-05",
  "completed": false
}
```

### AI Request

```json
{
  "inputText": "Built REST APIs using Spring Boot and MySQL.",
  "applicationId": 1
}
```

## Build and Test

```bash
./mvnw clean test
```

## Common Issues

- `403 Forbidden` on protected endpoints:
  - Missing/invalid/expired JWT token
  - Ensure `Authorization: Bearer <token>` is set

- AI endpoints fail:
  - Ensure Ollama is running (`ollama serve`)
  - Ensure model is pulled (`ollama list`)
  - Ensure model name matches property (`spring.ai.ollama.chat.model=qwen2.5:3b`)

- Ollama model is slow on first response:
  - This is normal, model loads into memory on first call
  - Subsequent calls are faster

- DB connection errors:
  - Verify MySQL is running
  - Verify DB name/user/password in `application.properties`



## Notes

- This project currently stores sensitive values in `application.properties`.
- For production, move secrets to environment variables or a secrets manager.
