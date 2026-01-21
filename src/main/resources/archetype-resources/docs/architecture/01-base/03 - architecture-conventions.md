# 🏷️ Clean Architecture Implementation Guideline (Updated)

This document defines the conventions and responsibilities for each layer of our **modular Clean Architecture**, now aligned with the updated structure:

```
api → transport → application → domain → external → bootstrap
```

The goal is to enforce **decoupling**, clear **flow of responsibility**, and **framework‑independent business logic**.

---

# 🧩 Layer Overview

```
api               (Controllers)
transport         (Handlers + Presenters + DTO Mapping)
application       (Use Case Orchestration)
domain            (Pure Business Logic)
external          (Technical Adapters)
bootstrap         (Spring Boot Runtime)
```

Each layer contains **one and only one responsibility**, communicating with the next through clear contracts.

---

# 1️⃣ API Layer

**Responsibility:**  
Expose REST endpoints and forward requests to the *transport* layer.

**Package:**  
`api.<feature>.controller`

### ✅ Rules

- No business logic.
- No domain references.
- Only HTTP concerns: routing, validation, error mapping.
- Delegation to transport uses:

```
handler.handle(request)
```

### Example

```java
@PostMapping("/examples")
public Mono<ResponseEntity<CreateExampleResponse>> create(
        @Valid @RequestBody CreateExampleRequest req) {
    return handler.handle(req)
            .map(ResponseEntity::ok);
}
```

---

# 2️⃣ Transport Layer

**Responsibility:**  
Glue between **API** and **Application**.

Performs:

- DTO → Command mapping
- Command → Use Case invocation
- Result → Response mapping
- i18n and error message formatting

**Package:**  
`transport.endpoints.<feature>.<usecase>`

### 📦 Structure

```
handler/     → Orchestrates the endpoint flow
presenter/   → InputPresenter & OutputPresenter
dto/         → Request and Response objects
```

### 🔄 Standard Flow

```
API Request
   ↓
InputPresenter.toCommand(request)
   ↓
UseCase.process(command)
   ↓
OutputPresenter.toResponse(result)
   ↓
API Response
```

### Example

```java
@Component
public class ExampleCreateHandler
        implements EndpointHandler<CreateExampleRequest, Mono<CreateExampleResponse>> {

    private final InputPresenter<CreateExampleRequest, CreateExampleCommand> in;
    private final OutputPresenter<CreateExampleResult, CreateExampleResponse> out;
    private final ExampleUseCase useCase;

    public ExampleCreateHandler(
            InputPresenter<CreateExampleRequest, CreateExampleCommand> in,
            OutputPresenter<CreateExampleResult, CreateExampleResponse> out,
            ExampleUseCase useCase) {
        this.in = in;
        t        his.out = out;
        this.useCase = useCase;
    }

    @Override
    public Mono<CreateExampleResponse> handle(CreateExampleRequest req) {
        CreateExampleCommand cmd = in.toCommand(req);
        return useCase.process(cmd).map(out::toResponse);
    }
}

```

---

# 3️⃣ Application Layer

**Responsibility:**  
Implements **use case orchestration**.

- Coordinates domain services and external ports
- Manages transactions
- Uses Reactive (`Mono`, `Flux`) if needed
- Converts technical errors → business errors

**Package:**  
`application.<feature>.usecases`

### 🧠 Naming Convention

Use **`process(...)`** for application service methods.

### Example

```java
@Service
public class CreateExampleUseCaseImpl implements CreateExampleUseCase {

    private final ExampleRepositoryPort repo;

    public CreateExampleUseCaseImpl(ExampleRepositoryPort repo) {
        this.repo = repo;
    }

    @Override
    @Transactional
    public Mono<CreateExampleResult> process(CreateExampleCommand cmd) {
        return Mono.fromCallable(() -> repo.register(cmd.toDomain()))
                   .map(CreateExampleResult::new);
    }
}
```

---

# 4️⃣ Domain Layer

**Responsibility:**  
Contains **pure business rules**.

- Entities, Value Objects, Aggregates
- Domain services
- Domain ports (interfaces)
- No Spring
- No WebFlux
- No annotations
- No framework dependencies

**Package:**  
`domain.core.<feature>`

### 🧩 Domain Ports

Ports define business intentions:

```java
public interface ExampleRepositoryPort {
    Example register(Example example);
    Optional<Example> load(Long id);
    boolean isNameTaken(String name);
}
```

### 🧠 Domain Use Case Contracts

Use **`handle(...)`** as the domain-standard verb.

```java
public interface CreateExampleUseCase {
    CreateExampleResult handle(CreateExampleCommand cmd);
}
```

---

# 5️⃣ External Layer

**Responsibility:**  
Implements *domain* and *application* ports using technical adapters:

- Database (JPA, R2DBC)
- File system
- WebClients (OpenAI)
- Kafka/RabbitMQ
- Caches

**Package:**  
`external.<type>.<feature>`

### Example Adapter

```java
@Repository
public class ExampleRepositoryAdapter implements ExampleRepositoryPort {

    private final ExampleR2dbcRepository repo;
    private final ExampleMapper mapper;

    public ExampleRepositoryAdapter(
            ExampleR2dbcRepository repo,
            ExampleMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    @Override
    public Example register(Example example) {
        return repo.save(mapper.toEntity(example))
                .map(mapper::toDomain)
                .block();
    }
}
```

---

# 6️⃣ Bootstrap Layer

**Responsibility:**  
Spring Boot application entrypoint + cross-module configuration.

**Package:**  
`bootstrap`

Contains:

- `Application.java`
- Global bean definitions
- Cross-cutting configurations (logging, security, mapping)

---

# 🔗 Global Flow Summary

```
Client REST Call
   ↓
api/                     (controller)
   ↓
transport/               (handler + presenters)
   ↓
application/             (use case orchestration)
   ↓
domain/                  (business logic + ports)
   ↓
external/                (infra implementations)
   ↑
application/             (assemble result)
   ↑
transport/               (map result → response)
   ↑
api/                     (return HTTP response)
```

---

# 🔤 Naming Conventions Summary

| Layer | Standard Verb | Meaning |
|--------|----------------|---------|
| api → transport | `handle(...)` | Forward a request |
| transport → application | `process(...)` | Trigger a use case |
| application → domain | `handle(...)` | Execute business logic |
| domain → external | domain verbs | Business-focused persistence intentions |

---

# 🧠 Final Principles

1. **Domain is pure and framework-free.**
2. **Transport is the only translation layer.**
3. **Application coordinates, not computes.**
4. **External adapters do technical work.**
5. **Dependencies flow inward only.**
6. **Errors and i18n belong to transport/API, not domain.**
7. **Business verbs rule the domain, not CRUD verbs.**

> *“The architecture is what remains when you remove frameworks.”*

