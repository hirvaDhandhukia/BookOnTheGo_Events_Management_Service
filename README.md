# Events Management Service

## Overview  
The **Events Management Service** is a core microservice responsible for managing **event creation, updates, deletion, and recommendations** based on user-selected locations in the **BookOnTheGo** platform. It allows **organizers** to create and manage events while enabling **users** to search, filter, and discover relevant events.  

This service also **categorizes events based on type, location, and popularity**, ensuring **personalized event recommendations** for users. It integrates with **other microservices**, including **Booking Service** (for ticket reservations), **Payment Service** (for handling transactions), and **Notification Service** (for sending alerts about upcoming events).  

To ensure **scalability and high availability**, this service is built using **Spring Boot** and **MySQL** for structured data storage. Event recommendations are optimized using **location-based filtering and user interests**. The system supports **pagination, sorting, and caching (via Redis)** to enhance query performance and reduce latency.  

#

## Tech Stack
- **Java 17**  
- **Spring Boot 3.x** (Spring Web, Spring Data JPA, Spring Security)  
- **MySQL** (AWS RDS for production)    
- **Docker & Kubernetes** (for containerization and deployment) 
- **JUnit & JoCoCo** (for TDD and unit testing) 