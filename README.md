## Order Subscription System

![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.x-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Database-4169E1?style=for-the-badge&logo=postgresql&logoColor=white)
![JUnit](https://img.shields.io/badge/JUnit-5-25A162?style=for-the-badge&logo=junit5&logoColor=white)

Spring Boot project implementing an order and subscription lifecycle.

The main goal of this project is to demonstrate a clean, modular
architecture inspired by Domain-Driven Design (DDD). The codebase
focuses on keeping business logic inside the domain layer while
separating application orchestration and infrastructure concerns.

## Architecture
The project follows a layered approach similar to Clean Architecture /
Hexagonal Architecture.

-   domain – core business logic (aggregates, value objects, domain
    events)
-   application – use cases and orchestration
-   infrastructure – persistence and framework-specific implementations
-   bootstrap – Spring Boot configuration and delivery layer (REST,
    scheduling)

The domain layer has no framework dependencies, making it easy to test
and reason about.

## Domain Model
The domain models the lifecycle of orders and subscriptions.

Key concepts:
-   Order
-   Subscription
-   Domain events describing state transitions

Subscriptions are associated with orders and follow a simple lifecycle:
1.  Start
2.  Cancel
3.  Expire

State transitions are validated inside aggregates to ensure that
business rules are always enforced.

## Business Rules
The system enforces several rules inside the domain model:

-   A subscription can only start for a paid order
-   An order can only have one active subscription
-   A subscription can be cancelled
-   Subscriptions automatically expire after a defined period
-   Every state change emits a domain event

Expiration is handled externally by a scheduled job which checks
subscriptions that passed their expiration date.


## Technology Stack
-   Java 17+
-   Spring Boot
-   Spring Data JPA
-   PostgreSQL
-   Flyway (database migrations)
-   Gradle (Kotlin DSL)

Project Structure

    order-domain
      Aggregates, value objects, domain events, business rules

    order-application
      Use cases, orchestration, repository ports, policies

    order-infrastructure
      JPA entities, repository implementations, mappers

    order-bootstrap
      Spring Boot configuration, REST controllers, schedulers

## Example Use Cases

The application supports several typical operations:
-   Start a subscription for a paid order
-   Prevent duplicate subscriptions for the same order
-   Cancel an active subscription
-   Automatically expire subscriptions after the configured time
-   Publish domain events on state transitions

## Running the Application
1.  Start PostgreSQL
2.  Configure database connection in application.yml
3.  Run the application:

    ./gradlew bootRun

## Author
Dominik Suliga (dominiks8318@gmail.com)
