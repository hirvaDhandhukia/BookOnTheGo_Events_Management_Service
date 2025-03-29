# Events Management Service

## Overview  
The **Events Management Service** is a core microservice responsible for managing **event creation, updates, deletion, and recommendations** based on user-selected locations in the **BookOnTheGo** platform. It allows **organizers** to create and manage events while enabling **users** to search, filter, and discover relevant events.  

This service is developed using **Spring** Boot and **MySQL**, and follows clean architectural patterns with controller, service, and repository layers. It integrates seamlessly with other microservices like Authentication, Notifications, and Payment in a microservice-based event booking platform. 

#

## Tech Stack
- **Java 17**  
- **Spring Boot 3.x** (Spring Web, Spring Data JPA, Spring Security)  
- **Maven** (for build and dependency management)
- **MySQL** (AWS RDS for production)    
- **Docker & Kubernetes** (for containerization and deployment) 
- **JUnit & JoCoCo** (for TDD and unit testing) 
- **JWT** (Authorization header)


## Features
- Create, update, and delete events (organizer only)
- Fetch all events or one by ID
- Book tickets as users
- Store event details with creator info, capacity, and images
- Maintain a list of booked users in the Booking table
- Basic JWT-based access control structure 
- RESTful API structure for easy frontend/backend communication



## Communication Flow
- POST/PUT requests send event or booking details as JSON payloads.
- Authenticated endpoints use Authorization: Bearer <jwt> headers.
- Booking and updates affect event capacity in real time.



## Architecture

### Layered Design

- **Controller Layer** - Handles incoming RESTful HTTP requests and maps them to appropriate service methods.  
- **Service Layer** - Implements business logic for event management and booking services.  
- **Repository Layer** - Handles data persistence with MySQL.  
- **Security Utility Layer** - JwtUtil.java is a placeholder utility intended to extract user identity from JWT tokens for securing role-based endpoints. 





## API Endpoints

### Event CRUD Operations
| Method | Endpoint                               | Description |
|--------|---------------------------------------|-------------|
| `POST` | `/api/events`                         | Create a new event |
| `GET` | `/api/events`                         | Get list of all events |
| `GET`  | `/api/events/{eventId}`               | Retrieve event details by ID |
| `PUT`  | `/api/events/{eventId}`               | Update an existing event (only if organizer)|
| `DELETE` | `/api/events/{eventId}`             | Delete an event (only if organizer) |
| `GET`  | `/api/events/organizer/{organizerId}` | Retrieve all events created by a specific organizer |
| `POST`  | `/api/events/{eventId}/book` | Book a ticket (JWT required) |




## Database Schema

### Events Table
| Column | Type | Description |
|--------|------|-------------|
| `id` | `BIGINT` | Primary key |
| `title` | `VARCHAR(255)` | Event name |
| `description` | `TEXT` | Event details |
| `date` | `TIMESTAMP` | Event date and time |
| `latitude` | `DECIMAL(10, 8)` | Location latitude |
| `longitude` | `DECIMAL(11, 8)` | Location longitude |
| `category` | `VARCHAR(100)` | Event category (e.g., Music, Tech, Sports) |
| `organizer_id` | `BIGINT` | Foreign key to the organizer |



## Running Locally

### Prerequisites
- Java 17
- MySQL installed (bookonthego DB created)
- Postman (to test endpoints)

### Steps
```bash
# 1. Clone the repo
git clone https://github.com/your-username/BookOnTheGo_Event_Service.git

# 2. Setup DB
CREATE DATABASE bookonthego;

# 3. Update application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/bookonthego
spring.datasource.username=root
spring.datasource.password=yourpassword

# 4. Run the service
mvn spring-boot:run

```


