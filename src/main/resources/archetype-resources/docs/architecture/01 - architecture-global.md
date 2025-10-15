# 🏷️ Architecture Overview

This project follows a **Clean Architecture** using Spring Boot with a **multi-module structure**.  
Its goal is to keep business logic **independent of frameworks**, isolate **technical details**, and ensure **clear modular boundaries** between layers.

---

## 🧩 Project Structure

```
backend/
├─ pom.xml                          # Parent Maven POM (module aggregator)
│
├─ domain/                          # Pure business logic — entities, value objects, use cases, ports
│
├─ application/                     # Application orchestration layer — coordinates use cases and transactions
│
├─ presentation/                    # Presentation mapping — converts between DTOs and domain models, handles i18n and errors
│
├─ external/                        # Outbound adapters — persistence, REST clients, messaging, etc.
│
├─ entrypoint/                      # Inbound adapters — REST controllers, web config, exception handling
│
└─ bootstrap/                       # Spring Boot runtime — main entrypoint and cross-module configuration
```

---

## ⚙️ Module Responsibilities

| Module | Responsibility |
|---------|----------------|
| **domain** | Contains **core business rules** — entities, value objects, domain services, and use case ports (input/output). Independent from frameworks. |
| **application** | Implements **application-level services** and orchestrates **use case execution**. Handles transactions and coordinates domain + external interactions. |
| **presentation** | Defines **DTOs, presenters, and converters** for mapping between the external world and the domain. Also manages **internationalization** and **error resolution**. |
| **external** | Contains **infrastructure implementations** of output ports — databases, HTTP clients, message brokers, file systems, etc. |
| **entrypoint** | Exposes the system through **controllers and APIs**. Handles validation, exception translation, and localization setup. Delegates business logic to presentation facades. |
| **bootstrap** | Contains the **main Spring Boot application** and high-level configuration (logging, model mappers, and bean registration across modules). |

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

- All messages (validation, errors, business texts) are defined under:
  ```
  presentation/src/main/resources/i18n/
  ```
- Organized by module (e.g. `example.properties`, `global_errors.properties`).
- Supports multi-language fallback (`example_fr.properties`, etc.).

---

## ⚙️ Error Management

- **Domain** defines `BaseBusinessException` and business error codes.
- **Presentation** layer resolves messages and HTTP status codes.
- **Entrypoint** layer (`GlobalExceptionHandler`) exposes consistent API error responses.

---

## 💡 Design Principles

1. **Business logic never depends on frameworks.**
2. **Each module has a single, clear responsibility.**
3. **Dependencies always flow inward.**
4. **Technical details are replaceable — domain stays stable.**
5. **Presentation and Entrypoint handle only input/output orchestration.**

---

> 🧠 *“Frameworks are tools — not architecture.”*

