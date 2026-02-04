# Order Subscription System

A modular Spring Boot application modeling an order and subscription lifecycle using Domain-Driven Design principles.  
The project demonstrates clean architecture, explicit domain modeling, and a strong separation of concerns between domain, application, and infrastructure layers.

---

## Key Characteristics

### Domain-Driven Design (DDD)
Rich domain model with aggregates, value objects, domain events, and explicit invariants.

### Clean Architecture / Hexagonal Architecture
Clear separation between:
- **domain** – pure business logic
- **application** – use cases and orchestration
- **infrastructure** – persistence and framework integrations
- **bootstrap** – Spring configuration and delivery mechanisms

### Event-driven domain model
Aggregates emit domain events (`SubscriptionStarted`, `Expired`, `Cancelled`) collected and published via an application-level publisher.

### Explicit business rules
No anemic models. State transitions and invariants are enforced inside aggregates, not in services.

---

## Business Scope

The system models:
- Order creation, payment, and cancellation
- Subscription lifecycle bound to an order:
  - start
  - cancel
  - expire (time-based)
- Automatic expiration of subscriptions via scheduled job

Subscription expiration is policy-driven and time-based (default: 30 days), with support for permanent subscriptions.

---

## Technical Stack

- Java 17+
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Flyway (database migrations)
- Gradle (Kotlin DSL)

---

## Module Overview

```text
order-domain
  - Aggregates, value objects, domain events, invariants

order-application
  - Use cases, domain orchestration, policies, repositories (ports)

order-infrastructure
  - JPA entities, repositories, mappers, adapters

order-bootstrap
  - Spring configuration, REST controllers, schedulers
```

## Notable Design Decisions
* No framework dependencies in the domain layer
> The domain is framework-agnostic and fully testable in isolation.

* Repositories as ports
> Application layer depends on interfaces, infrastructure provides adapters.

* Time-based logic isolated from the domain
> Expiration is triggered externally (scheduler), domain only validates rules.

* Strong typing
> Identifiers (OrderId, SubscriptionId) and value objects instead of primitives.

## Example Use Cases
* Start a subscription for a paid order
* Prevent duplicate subscriptions for the same order
* Cancel an active subscription
* Automatically expire subscriptions past their expiration date
* Publish domain events on every state transition

Running the Application
1. Start PostgreSQL
2. Configure database connection in application.yml
3. Start the application:
     ```./gradlew bootRun```
