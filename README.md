# TaskMaster

TaskMaster is a RESTful task and team management application built with Java and Spring Boot. It supports user profiles, team membership, task assignment and status tracking, task comments, search and sorting, and file attachments.

The project is an assignment application under `TaskManager/TaskMaster` and uses MySQL with Spring Data JPA.

## Features

- Register users with validated email, phone number, username, and password
- Store passwords securely using BCrypt
- View and update user profiles
- Create, view, and update teams
- Add and remove team members
- List teams created by or joined by a user
- Create, retrieve, update, assign, and delete tasks
- Filter assigned tasks by status
- Sort tasks by due date, creation date, title, or status
- Search a user's tasks by title or description
- Update task status: `OPEN`, `IN_PROGRESS`, or `COMPLETED`
- Add and retrieve comments on tasks
- Upload multiple attachments to a task
- Centralized validation and resource exception responses

## Technology Stack

| Category | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 4.1.2 |
| Web | Spring Web MVC |
| Persistence | Spring Data JPA / Hibernate |
| Database | MySQL |
| Validation | Jakarta Bean Validation |
| Security utility | Spring Security Crypto / BCrypt |
| Build tool | Apache Maven |
| Boilerplate reduction | Lombok |
| Testing | Spring Boot Test |

## Project Structure

```text
TaskManager/
|-- README.md
`-- TaskMaster/
    |-- pom.xml
    |-- mvnw
    |-- mvnw.cmd
    `-- src/
        |-- main/
        |   |-- java/com/assignment/TaskMaster/
        |   |   |-- TaskMasterApplication.java
        |   |   |-- config/
        |   |   |-- controller/
        |   |   |-- dto/
        |   |   |-- entity/
        |   |   |-- exception/
        |   |   |-- repository/
        |   |   `-- service/
        |   `-- resources/
        |       `-- application.properties
        `-- test/
            `-- java/com/assignment/TaskMaster/
                `-- TaskMasterApplicationTests.java
```

### Package Overview

- `config` - Creates the BCrypt password encoder bean.
- `controller` - Exposes the REST API and maps HTTP requests to services.
- `dto` - Defines validated request objects and JSON response objects.
- `entity` - Contains JPA entities and the task status enum.
- `repository` - Provides Spring Data JPA repository interfaces and task queries.
- `service` - Implements the application and validation logic.
- `exception` - Defines domain exceptions and global REST error handling.

## Domain Model

### User

A user contains a generated ID, username, unique email address, phone number, BCrypt-hashed password, and creation timestamp.

### Team

A team has a name, a creator, and a creation timestamp. Team membership is represented separately through `Teammate`.

### Teammate

The `Teammate` join entity associates a user with a team and records the join time. A team-user pair is unique.

### Task

A task has a title, description, due date, status, creator, optional assignee, optional team, attachment filenames, and creation/update timestamps.

Task statuses:

- `OPEN`
- `IN_PROGRESS`
- `COMPLETED`

### Comment

A comment contains text, the task it belongs to, the creating user, and a creation timestamp.

## Database Configuration

Create a MySQL database named `taskmanager`:

```sql
CREATE DATABASE taskmanager;
```

The application connects with the following defaults from `TaskMaster/src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/taskmanager
spring.datasource.username=root
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect

app.upload-dir=uploads
spring.servlet.multipart.max-file-size=5MB
spring.servlet.multipart.max-request-size=5MB
```

Set the real MySQL password in `spring.datasource.password` before running the application. The repository configuration does not include a usable password value. Hibernate with `ddl-auto=update` will create or update tables when the application starts.

## Getting Started

### Prerequisites

- JDK 17
- Maven-compatible environment, or use the included Maven Wrapper
- MySQL running on port `3306`

### Run on Windows

```powershell
cd TaskManager\TaskMaster
.\mvnw.cmd spring-boot:run
```

### Run on Linux or macOS

```bash
cd TaskManager/TaskMaster
./mvnw spring-boot:run
```

The application starts at:

```text
http://localhost:8080
```

### Build

```bash
cd TaskManager/TaskMaster
./mvnw clean package
```

On Windows, replace `./mvnw` with `.\mvnw.cmd`.

### Run Tests

```bash
cd TaskManager/TaskMaster
./mvnw test
```

The current test suite verifies application context loading with `TaskMasterApplicationTests`.

## REST API

All endpoints return JSON except attachment endpoints and operations that use `204 No Content`.

### Authentication and Users

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/auth/register` | Register a user |
| `GET` | `/api/users/{id}` | Get a user profile |
| `PUT` | `/api/users/{id}` | Update username and phone |

Example registration body:

```json
{
  "username": "sunny",
  "email": "sunny@example.com",
  "phone": "+919876543210",
  "password": "strongpass123"
}
```

Passwords must contain at least 8 characters. Phone numbers must match the application's international phone pattern.

### Teams

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/teams` | Create a team |
| `GET` | `/api/teams/{id}` | Get a team |
| `GET` | `/api/teams?createdBy={userId}` | List teams created by a user |
| `PUT` | `/api/teams/{id}` | Update a team name |

Create team body:

```json
{
  "teamname": "Backend Team",
  "createdBy": 1
}
```

Update team body:

```json
{
  "teamName": "Platform Team"
}
```

### Team Membership

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/teams/{teamId}/members` | Add a user to a team |
| `GET` | `/api/teams/{teamId}/members` | List team members |
| `DELETE` | `/api/teams/{teamId}/members/{userId}` | Remove a member |
| `GET` | `/api/users/{userId}/teams` | List teams joined by a user |

Add-member body:

```json
{
  "userId": 2
}
```

The team creator cannot be removed.

### Tasks

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/tasks` | Create a task |
| `GET` | `/api/tasks/{id}` | Get a task |
| `GET` | `/api/tasks?assignedTo={id}` | List tasks assigned to a user |
| `GET` | `/api/tasks/search?assignedTo={id}&keyword={text}` | Search assigned tasks |
| `PUT` | `/api/tasks/{id}` | Update title, description, and due date |
| `PATCH` | `/api/tasks/{id}/status` | Update task status |
| `PATCH` | `/api/tasks/{id}/assign` | Assign a task to a user |
| `DELETE` | `/api/tasks/{id}` | Delete a task and its comments |
| `POST` | `/api/tasks/{id}/attachments` | Upload an attachment |

Create-task body:

```json
{
  "title": "Implement API",
  "description": "Create and test the REST endpoints",
  "dueDate": "2026-12-15",
  "assignedTo": 2,
  "teamId": 1,
  "createdBy": 1
}
```

Task listing query parameters:

| Parameter | Required | Default | Values |
|---|---|---|---|
| `assignedTo` | Yes | - | User ID |
| `status` | No | - | `OPEN`, `IN_PROGRESS`, `COMPLETED` |
| `sortBy` | No | `dueDate` | `dueDate`, `createdAt`, `title`, `status` |
| `direction` | No | `asc` | `asc`, `desc` |

Example:

```text
GET /api/tasks?assignedTo=2&status=OPEN&sortBy=dueDate&direction=asc
```

Update status body:

```json
{
  "status": "IN_PROGRESS"
}
```

Assign task body:

```json
{
  "assigneeId": 2
}
```

When a task belongs to a team, assignment is rejected unless the assignee is a team member.

Upload an attachment with a multipart form-data request:

```text
POST /api/tasks/1/attachments
Content-Type: multipart/form-data

file=@/path/to/document.pdf
```

Attachments are saved under `app.upload-dir`. Filenames are sanitized and stored with generated prefixes. The configured maximum file/request size is 5 MB.

### Comments

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/tasks/{taskId}/comments` | Add a comment |
| `GET` | `/api/tasks/{taskId}/comments` | List task comments |
| `DELETE` | `/api/comments/{commentId}` | Delete a comment |

Create-comment body:

```json
{
  "content": "The implementation is ready for review.",
  "createdBy": 1
}
```

## Validation and Error Responses

`@RestControllerAdvice` returns field-level validation errors as a JSON map with HTTP `400 Bad Request`.

```json
{
  "title": "Task title is required",
  "password": "size must be between 8 and 2147483647"
}
```

The API also defines responses for:

| HTTP status | Meaning |
|---|---|
| `400 Bad Request` | Validation failure, invalid sort/direction, invalid upload, or creator removal attempt |
| `404 Not Found` | User, team, teammate, task, or comment not found |
| `409 Conflict` | Duplicate email or duplicate team membership |

## Response Examples

User response:

```json
{
  "id": 1,
  "username": "sunny",
  "email": "sunny@example.com",
  "phone": "+919876543210",
  "createdAt": "2026-11-22T20:12:38"
}
```

Team response:

```json
{
  "id": 1,
  "teamName": "Backend Team",
  "createdById": 1,
  "createdByUsername": "sunny",
  "createdAt": "2026-11-22T20:12:38"
}
```

Task response:

```json
{
  "id": 1,
  "title": "Implement API",
  "description": "Create and test the REST endpoints",
  "dueDate": "2026-12-15",
  "status": "OPEN",
  "assignedToId": 2,
  "assignedToUsername": "alex",
  "teamId": 1,
  "createdById": 1,
  "attachment": [],
  "createdAt": "2026-11-22T20:12:38",
  "updatedAt": "2026-11-22T20:12:38"
}
```

## Implementation Notes

- The project currently exposes registration and profile operations, but no login/JWT/session authentication endpoint or Spring Security filter chain.
- Password registration uses BCrypt, while email uniqueness and some resource lookups are enforced through service/repository logic.
- Uploaded attachment filenames are stored in a comma-separated string in the task entity and returned as a list.
- The team and task data models use lazy JPA relationships.
- Hibernate is configured to update the schema automatically in the current local configuration.