# 🏷️ Architecture Overview

This project follows a **Clean Architecture** with a modular Spring Boot setup.  
Each module has a **single responsibility**, ensuring maintainability, testability, and separation of concerns.

---

## 🧩 Project Structure

```
backend/
├─ pom.xml                     # Maven aggregator
│
├─ domain/                    # Pure domain (POJO) — entities, value objects, domain ports
├─ application/               # Use cases — business orchestration, may use reactive types
├─ transport/                 # Mapping layer — endpoint handlers, presenters, DTO
├─ api/                       # HTTP entrypoint — WebFlux controllers & routing
├─ external/                  # Technical adapters — DB, filesystem, OpenAI, etc.
└─ bootstrap/                 # Spring Boot main module & system-level configuration
```

---

## ⚙️ Module Responsibilities

### **1. domain/**
- 100% **framework-free**
- Only **POJOs**, records, Java types
- Contains:
    - Entities & Value Objects
    - Domain business rules (pure functions)
    - **Domain ports** (interfaces — synchronous)

```
No Spring
No Reactor
No annotations
```

---

### **2. application/**
- Implements **use cases**
- Orchestrates domain + external
- May use **Mono/Flux**
- Contains:
    - Use case classes (`PromptRunnerUseCase`, `PromptQueryUseCase`)
    - **Application ports** (e.g. `ChatClient`, `PromptFileLoader`)
    - Transaction boundaries
    - Rules that involve multiple repositories

---

### **3. transport/**
Layer between API and application.  
Responsible for **all input/output conversions**.

Contains:

- `EndpointHandler<I,O>`
- `InputPresenter<DTO, Command>`
- `OutputPresenter<Result, DTO>`
- Request/Response DTO (pure)

Structure example:

```
transport/
  common/
    contracts/
  endpoints/
    prompt/
      run/
        handler/
        presenter/
        dto/
```

No business logic here.

---

### **4. api/**
- WebFlux controllers
- HTTP routing
- Validation (`@Valid`)
- Exception handling
- Delegates to `transport` handlers

---

### **5. external/**
Implements **application and domain ports**:

- Repositories (R2DBC, JDBC…)
- File loaders (Text, JSON…)
- OpenAI / REST clients
- Messaging adapters

Contains Spring + technical libraries.

---

### **6. bootstrap/**
- Main Spring Boot application
- Global configuration
- Module wiring
- Logging configuration
- Environment setup

---

## 🔗 Request Flow

```
Client (REST)
   ↓
api/                → Controllers
   ↓
transport/         → Handlers + Presenters (DTO ↔ Commands)
   ↓
application/       → Use Case orchestration
   ↓
domain/            → Pure business logic
   ↓
external/          → Technical implementations
   ↑
application/       ← Builds Result
   ↑
transport/         ← Presenter converts Result → DTO
   ↑
api/               ← Returns Response
```

---

## 💡 Key Principles

1. **Domain is pure Java.**
2. **Application orchestrates use cases.**
3. **Transport handles mapping only.**
4. **API handles protocols only.**
5. **External handles technical details.**
6. **Dependencies always flow inward.**

> 🧠 *Infrastructure changes. Business rules don’t.*
