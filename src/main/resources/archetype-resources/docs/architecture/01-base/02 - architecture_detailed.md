# 🏷️ Architecture Overview

This project implements a **Clean Architecture** using Spring Boot with a **multi-module structure**.  
The goal is to keep business rules independent of frameworks and I/O, and to isolate infrastructure, wiring, and presentation logic cleanly.

---

## 🧩 Project Layout

```
backend/
├─ pom.xml                          # Parent POM (packaging = pom)
│
├─ domain/                          # Pure business logic (entities, value objects, ports)
│  ├─ pom.xml
│  └─ src/main/java/com/example/domain/
│     ├─ core/<context>/            # Each subdomain or bounded context
│     │  ├─ model/                  # Core domain models: Entities, Value Objects, Aggregates
│     │  ├─ ports/                  # Domain ports (input/output) — synchronous, framework-free
│     │  └─ services/               # Domain services (pure business rules)
│     └─ commons/                   # Shared domain types: base exceptions, error codes, utilities
│
├─ application/                     # Use case orchestration layer (reactive allowed)
│  ├─ pom.xml
│  └─ src/main/java/com/example/application/
│     └─ <context>/usecases/        # Orchestrates domain + external ports
│        ├─ commands/               # Input command models
│        ├─ queries/                # Query models
│        ├─ results/                # Output result models
│        └─ ...                     # Calls pure domain + external implementations
│
├─ transport/                       # DTO mapping layer between API and Application
│  ├─ pom.xml
│  └─ src/main/java/com/example/transport/
│     ├─ common/contracts/          # EndpointHandler, InputPresenter, OutputPresenter
│     └─ endpoints/<context>/       # Grouped by features
│        ├─ <usecase>/dto/          # Request/Response DTOs
│        ├─ <usecase>/presenter/    # Mapping between DTO ↔ Commands/Results
│        └─ <usecase>/handler/      # Delegates to application layer
│
├─ api/                             # Web entrypoint (REST, WebFlux)
│  ├─ pom.xml
│  └─ src/main/java/com/example/api/
│     ├─ controllers/<context>/     # REST controllers for each feature
│     └─ advice/                    # Global exception translation
│
├─ external/                        # Infrastructure adapters (DB, HTTP clients, FS, AI)
│  ├─ pom.xml
│  └─ src/main/java/com/example/external/
│     ├─ persistence/               # Database entities + repository implementations
│     ├─ file/                      # File-system adapters
│     ├─ openai/                    # OpenAI WebClient adapter
│     └─ messaging/                 # Kafka/RabbitMQ implementations
│
└─ bootstrap/                       # Spring Boot runtime & application wiring
   ├─ pom.xml
   └─ src/main/java/com/example/bootstrap/
      ├─ Application.java            # Main Spring Boot launcher
      └─ configuration/              # Cross-module configuration beans
```

---

## ⚙️ Module Responsibilities

| Module | Responsibility |
|---------|----------------|
| **domain** | Pure business rules. Entities, value objects, domain services, and synchronous domain ports. Zero framework dependency. |
| **application** | Executes use cases, coordinates domain + external. May use Reactive. Defines application ports for external adapters. |
| **transport** | Performs DTO mapping: request → command, result → response. Contains presenters and endpoint handlers. |
| **api** | REST/Web entrypoint using WebFlux. Routing, validation, and exception exposure. Delegates to `transport` handlers. |
| **external** | Implements ports using infrastructure: database, filesystem, HTTP/OpenAI, messaging. No business logic. |
| **bootstrap** | Bootstraps the Spring application. Loads global config and wires modules together. |

---

## 🔗 Flow Overview

```
Client (REST)
   ↓
api/                → Controllers (routing, validation)
   ↓
transport/         → Presenters & EndpointHandlers (DTO ↔ Commands)
   ↓
application/       → Use cases (orchestration, transactions)
   ↓
domain/            → Pure business logic (entities, services, ports)
   ↓
external/          → Technical implementations (DB, APIs, AI, Files)
   ↑
application/       ← Collects and formats results
   ↑
transport/         ← Maps Result → Response DTO
   ↑
api/               ← Sends HTTP JSON response
```

---

## 🌍 Internationalization

- All i18n messages are located under:

```
transport/src/main/resources/i18n/
```

- Naming conventions:
    - `feature.properties` / `feature_fr.properties`
    - `global_errors.properties` / `global_errors_fr.properties`

- Transport layer is responsible for text localization and message lookup.

---

## ⚙️ Error Handling

- **domain**: Pure business exceptions (no HTTP).
- **application**: May aggregate or convert domain errors.
- **transport**: Resolves and formats localized error messages.
- **api**: Maps errors to HTTP status codes and returns structured responses.

Example error flow:

```
DomainError → Application catches → Transport formats → API returns JSON error
```

---

## 💡 Design Principles

1. **Domain is immutable and framework-free.**
2. **Application orchestrates workflows but contains no presentation logic.**
3. **Transport converts all external formats (DTOs & messages).**
4. **API only routes and exposes.**
5. **External is replaceable and contains all infrastructure details.**
6. **Dependencies always point inward:**
   ```
   external → application → domain
   api → transport → application → domain
   ```

---

> 🧠 *“Frameworks are tools. The architecture is what remains when tools evolve.”*
