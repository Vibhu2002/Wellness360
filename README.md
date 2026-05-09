# Task Management REST API (Spring Boot)

A RESTful task management API built with Java and Spring Boot.

## Features
- CRUD endpoints for tasks
- Mark task as complete endpoint
- Input validation and global error handling
- In-memory storage (ConcurrentHashMap)
- Basic authentication (HTTP Basic)
- Unit test for task completion logic

## Task model
- `id` (Long)
- `title` (String)
- `description` (String)
- `due_date` (LocalDate)
- `status` (`pending`, `in_progress`, `completed`)
- `created_at` (Instant)
- `updated_at` (Instant)

## Authentication
All endpoints require HTTP Basic auth.

Credentials can be set with environment variables:
- `TASK_API_USERNAME`
- `TASK_API_PASSWORD`

Defaults (if env vars are not set):
- Username: `admin`
- Password: `admin123`

## Endpoints
- `GET /tasks`
- `GET /tasks/{id}`
- `POST /tasks`
- `PUT /tasks/{id}`
- `DELETE /tasks/{id}`
- `PATCH /tasks/{id}/complete`

## Run locally
```bash
export TASK_API_USERNAME=admin
export TASK_API_PASSWORD=admin123
mvn spring-boot:run
```

## Run tests
```bash
mvn test
```

## End-to-end test steps for all task APIs (using env vars)
In one terminal:
```bash
export TASK_API_USERNAME=admin
export TASK_API_PASSWORD=admin123
mvn spring-boot:run
```

In another terminal:
```bash
export BASE_URL=http://localhost:8080
export TASK_API_USERNAME=admin
export TASK_API_PASSWORD=admin123
```

1) Create task (`POST /tasks`):
```bash
curl -i -u "$TASK_API_USERNAME:$TASK_API_PASSWORD" -X POST "$BASE_URL/tasks" \
  -H "Content-Type: application/json" \
  -d '{"title":"Finish case study","description":"Implement API and tests","due_date":"2026-05-15","status":"in_progress"}'
```

2) Get all tasks (`GET /tasks`):
```bash
curl -i -u "$TASK_API_USERNAME:$TASK_API_PASSWORD" "$BASE_URL/tasks"
```

3) Get task by id (`GET /tasks/{id}`):
```bash
curl -i -u "$TASK_API_USERNAME:$TASK_API_PASSWORD" "$BASE_URL/tasks/1"
```

4) Update task (`PUT /tasks/{id}`):
```bash
curl -i -u "$TASK_API_USERNAME:$TASK_API_PASSWORD" -X PUT "$BASE_URL/tasks/1" \
  -H "Content-Type: application/json" \
  -d '{"title":"Finish updated case study","description":"Add docs and tests","due_date":"2026-05-20","status":"pending"}'
```

5) Mark complete (`PATCH /tasks/{id}/complete`):
```bash
curl -i -u "$TASK_API_USERNAME:$TASK_API_PASSWORD" -X PATCH "$BASE_URL/tasks/1/complete"
```

6) Delete task (`DELETE /tasks/{id}`):
```bash
curl -i -u "$TASK_API_USERNAME:$TASK_API_PASSWORD" -X DELETE "$BASE_URL/tasks/1"
```

7) Confirm deleted (should return 404):
```bash
curl -i -u "$TASK_API_USERNAME:$TASK_API_PASSWORD" "$BASE_URL/tasks/1"
```

## Design decisions and assumptions
1. **In-memory store**: Uses `ConcurrentHashMap` + `AtomicLong` for thread-safe storage and ID generation.
2. **Validation**: `title` and `description` are required; `due_date` must be today or future.
3. **Status default**: New task defaults to `pending` if status is omitted.
4. **Error handling**: Returns consistent JSON for not found, validation, and generic failures.
5. **Security**: Basic auth added as a lightweight bonus feature and credentials can come from environment variables.
