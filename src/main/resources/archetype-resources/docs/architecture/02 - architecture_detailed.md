# 🏷️ Architecture Overview

This project implements a **Clean Architecture** using Spring Boot with a **multi-module structure**.  
The goal is to keep business rules independent of frameworks and I/O, and to isolate infrastructure, wiring, and presentation logic cleanly.

---

## 🧩 Project Layout

```
backend/
├─ pom.xml                          # Parent POM (packaging = pom)
│
├─ domain/                          # Pure business logic (entities, value objects, ports, use cases)
│  ├─ pom.xml
│  └─ src/main/java/com/example/domain/
│     ├─ core/<context>/            # Each subdomain or bounded context
│     │  ├─ model/                  # Core domain models: Entities, Value Objects, Aggregates
│     │  ├─ operations/             # Application-specific actions: Commands, Queries, Results
│     │  ├─ ports/                  # Input and Output interfaces (use case & persistence contracts)
│     │  └─ usecases/               # Business rules implementations (pure domain logic)
│     └─ commons/                   # Shared domain types: base exceptions, error codes, utilities
│
├─ application/                     # Use case orchestration layer (transactional boundary)
│  ├─ pom.xml
│  └─ src/main/java/com/example/application/
│     └─ services/<context>/        # Application services calling domain use cases and ports
│        ├─ impl/                   # Internal implementations (optional)
│        └─ ...                     # Input commands → domain logic → results mapping
│
├─ presentation/                    # Data mapping, i18n, and error management
│  ├─ pom.xml
│  ├─ src/main/java/com/example/presentation/
│  │  ├─ common/                    # Shared configs, message source, i18n, and error resolvers
│  │  │  ├─ configuration/          # MessageSource, ModelMapper, etc.
│  │  │  ├─ errors/                 # DefaultErrorResponse + HTTP resolvers
│  │  │  └─ i18n/                   # BusinessErrorMessageResolver and message translation
│  │  ├─ config/                    # Bean configurations specific to presentation layer
│  │  └─ representations/           # Maps API DTOs ↔ domain objects
│  │     └─ <context>/              # Organized per bounded context (e.g. example/)
│  │        ├─ input/               # Requests, converters, presenters (inbound)
│  │        ├─ output/              # Responses, converters, presenters (outbound)
│  │        └─ facade/              # Orchestrates presentation logic for each context
│  └─ src/main/resources/i18n/      # Resource bundles for i18n and error messages
│
├─ external/                        # Infrastructure adapters (persistence, HTTP, messaging)
│  ├─ pom.xml
│  └─ src/main/java/com/example/external/
│     ├─ persistence/               # Database layer — JPA entities and repository adapters
│     ├─ httpclient/                # REST client implementations for outbound APIs
│     └─ messaging/                 # Message brokers (Kafka, RabbitMQ, etc.)
│
├─ entrypoint/                      # Entry adapters (HTTP, CLI, etc.)
│  ├─ pom.xml
│  └─ src/main/java/com/example/entrypoint/
│     ├─ rest/                      # REST controllers + exception handlers
│     │  ├─ endpoints/<context>/    # REST API endpoints organized by context
│     │  └─ advice/                 # Global error handling (delegates to presentation)
│     └─ config/                    # Web configuration (localization, interceptors, etc.)
│  └─ src/main/resources/           # Web-layer resources (logging, templates, etc.)
│
└─ bootstrap/                       # Application entrypoint and global configuration
   ├─ pom.xml
   └─ src/main/java/com/example/bootstrap/
      ├─ Application.java            # Main Spring Boot launcher
      └─ configuration/              # Cross-module configuration (e.g. mappers, filters)
```

---

## ⚙️ Module Responsibilities

| Module | Responsibility |
|---------|----------------|
| **domain** | Contains **core business logic**: entities, value objects, and domain services. Defines contracts through ports (input/output). Framework-agnostic. |
| **application** | Coordinates domain interactions. Executes use cases, handles transactions, and manages the lifecycle of domain processes. |
| **presentation** | Bridges **entrypoint** and **domain** layers: maps DTOs, handles localization (i18n), and formats errors. |
| **external** | Implements domain output ports (repositories, API clients, messaging) using technical adapters. No domain logic. |
| **entrypoint** | Exposes APIs and entry mechanisms (REST, CLI). Delegates all logic to presentation. Handles request validation and exception translation. |
| **bootstrap** | Configures and starts the Spring Boot runtime. Wires all modules together and provides shared bean configuration. |

---

## 🔗 Flow Overview

```
Entrypoint (REST, Web)
   ↓
Presentation (DTO → Command → Response)
   ↓
Application (Use case orchestration, @Transactional)
   ↓
Domain (Business logic, Entities, Ports)
   ↓
External (Persistence, APIs, Messaging)
```

---

## 🌍 Internationalization

- All i18n files are stored under `presentation/src/main/resources/i18n/`
- File naming convention:
  - `example.properties`, `example_fr.properties` — domain-specific messages
  - `global_errors.properties`, `global_errors_fr.properties` — shared system errors
- Messages are loaded dynamically via `MessageResourceConfiguration`

---

## ⚙️ Error Handling

- **Domain** defines error codes and exceptions (e.g. `BaseBusinessException`)
- **Presentation** layer resolves localized messages and HTTP mappings
- **Entrypoint** layer exposes unified JSON responses via `GlobalExceptionHandler`

---

## 💡 Design Principles

1. Business logic remains **pure and independent** of any framework.
2. Each module has **a single, well-defined responsibility**.
3. All dependencies **flow inward** — from infrastructure to domain.
4. Presentation focuses on **data translation, not logic**.
5. Technical details (DB, HTTP, Kafka) are **plug-in adapters**.

---

> 🧠 *“The architecture is what stays when frameworks and tools change.”*

