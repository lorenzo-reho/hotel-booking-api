# Hotel Booking API

REST API for hotel reservation management developed with Spring Boot.

## Tech Stack

| Category | Technologies |
|----------|--------------|
| Language | Java 21 |
| Framework | Spring Boot 3 |
| Security | Spring Security, JWT |
| Persistence | Spring Data JPA, Hibernate |
| Database | PostgreSQL |
| Build Tool | Maven |
| Containerization | Docker Compose |

## API Overview

### Authentication

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/auth/register` | Register a new user |
| POST | `/api/auth/login` | Authenticate and obtain a JWT |

### Rooms

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/rooms` | Get all rooms |
| GET | `/api/rooms/{id}` | Get room details |
| POST | `/api/rooms` | Create a room *(ADMIN)* |
| PATCH | `/api/rooms/{id}` | Update a room *(ADMIN)* |
| DELETE | `/api/rooms/{id}` | Delete a room *(ADMIN)* |

### Bookings

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/bookings` | Get all bookings *(ADMIN)* |
| GET | `/api/bookings/my` | Get authenticated user's bookings |
| GET | `/api/bookings/{id}` | Get booking details |
| POST | `/api/bookings` | Create a booking |
| PATCH | `/api/bookings/{id}` | Update a booking |
| DELETE | `/api/bookings/{id}` | Delete a booking |

### Users

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/users` | Get all users *(ADMIN)* |
| GET | `/api/users/me` | Get authenticated user's profile |
| GET | `/api/users/{id}` | Get user details *(ADMIN)* |
| PATCH | `/api/users/{id}` | Update a user *(ADMIN)* |
| DELETE | `/api/users/{id}` | Delete a user *(ADMIN)* |

## Error Handling

The API uses HTTP status codes to communicate the result of a request.

| Status Code | Exception | Description |
|------------|------------------------|-------------|
| 404 Not Found | `NoResourceFoundException` | The requested API endpoint does not exist |
| 404 Not Found | `NoSuchElementException` | The requested resource was not found |
| 405 Method Not Allowed | `HttpRequestMethodNotSupportedException` | The HTTP method is not supported for the requested endpoint |
| 409 Conflict | `EmailAlreadyExistsException` | An account with the specified email already exists |
| 409 Conflict | `RoomNumberAlreadyExistsException` | A room with the specified number already exists |
| 409 Conflict | `RoomAlreadyBookedException` | The room is already booked for the selected period |
| 409 Conflict | `IllegalStateException` | The requested operation cannot be performed due to the current resource state |
| 500 Internal Server Error | `Exception` | Unexpected server-side error |

## Installation

### 1. Clone the repository

```bash
git clone https://github.com/lorenzo-reho/hotel-booking-api.git
cd hotel-booking-api
```

### 2. Configure the environment

Create a `.env` file starting from `.env.example` and configure your PostgreSQL credentials.

### 3. Start PostgreSQL

```bash
docker compose up -d
```

### 4. Run the application

The application can be started directly from IntelliJ IDEA:

1. Open the project in IntelliJ IDEA.
2. Wait for Maven dependencies to be downloaded.
3. Run the main Spring Boot class:

```
BookingApplication.java
```

Alternatively, the application can be started from the command line:

```bash
./mvnw spring-boot:run
```