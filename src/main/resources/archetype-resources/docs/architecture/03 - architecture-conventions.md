# 🧩 Clean Architecture Implementation Guideline

> **Goal:**  
> This document defines the conventions and responsibilities for each layer of our **modular Clean Architecture**, from **Entrypoint** to **External Adapters**.  
> It ensures consistent naming, clear flow of responsibility, and total decoupling between technical and business concerns.

---

## 🛠️ Overview

```
Entrypoint → Presentation → Application → Domain → External
```

Each layer has **one clear responsibility** and communicates with the next using **well-defined contracts**.  
Business logic remains framework-independent and testable.

---

## 1️⃣ Entrypoint Layer

**Responsibility:**  
Expose APIs (REST controllers, messaging endpoints, etc.) and delegate to the Presentation layer.  
No business logic or domain model exposure.

**Typical package:**  
`entrypoint.rest.endpoints.<module>.controller`

### ✅ Naming and verbs
- Controller method verbs must match HTTP semantics (`create`, `update`, `delete`, `get`, `list`, etc.).
- Delegation to the facade always uses the neutral verb **`present(...)`**.

```java
@PostMapping("/examples")
public ResponseEntity<CreateExampleResponse> create(@RequestBody CreateExampleRequest req) {
    return ResponseEntity.status(HttpStatus.CREATED).body(facade.present(req));
}
```

### ✅ Responsibilities
- Validate input (@Valid, DTO constraints)
- Handle localization headers / language preferences
- Map exceptions (via `@RestControllerAdvice`)
- Log trace IDs for observability
- Never depend on `domain` or `external` packages directly

---

## 2️⃣ Presentation Layer

**Responsibility:**  
Acts as an **orchestration and mapping layer** between the API (Entrypoint) and the Application layer.  
Handles DTO ↔ Command ↔ Result ↔ Response conversion and message localization.

**Typical package:**  
`presentation.representations.<module>.facade`

### ✅ Facade conventions
- Expose a single, neutral verb **`present(...)`** for controllers.
- Internally, translate the request into a command and delegate to the Application layer.

```java
@Component
@RequiredArgsConstructor
public class ExampleEndpointFacade {

    private final InputPresenter<CreateExampleRequest, CreateExampleCommand> inPresenter;
    private final OutputPresenter<CreateExampleResult, CreateExampleResponse> outPresenter;
    private final ExampleService app;

    public CreateExampleResponse present(CreateExampleRequest req) {
        var cmd = inPresenter.toCommand(req);
        var result = app.process(cmd);      // delegate to Application
        return outPresenter.toResponse(result);
    }
}
```

### ✅ Verbs used between layers
| From | To | Verb | Description |
|------|----|------|-------------|
| Entrypoint | Presentation | `present(...)` | The controller delegates presentation logic |
| Presentation | Application | `process(...)` | Launch the use case/application workflow |

---

## 3️⃣ Application Layer

**Responsibility:**  
Implements the **application flow** and orchestrates **use cases**.  
Coordinates ports, handles transactions, and translates exceptions.

**Typical package:**  
`application.services.<module>`

### ✅ Service conventions
- Each service represents a **module boundary**.
- Methods use the neutral verb **`process(...)`** to trigger a domain use case.
- Transactions are managed here (`@Transactional`).

```java
@Service
@RequiredArgsConstructor
public class ExampleServiceImpl implements ExampleService {

    private final CreateExampleUseCase useCase;

    @Override
    @Transactional
    public CreateExampleResult process(CreateExampleCommand command) {
        return useCase.handle(command);
    }
}
```

### ✅ Verbs used between layers
| From | To | Verb | Description |
|------|----|------|-------------|
| Presentation | Application | `process(...)` | Start an application flow |
| Application | Domain | `handle(...)` | Execute a specific domain use case |

---

## 4️⃣ Domain Layer

**Responsibility:**  
Contains **pure business logic** — entities, value objects, domain services, and use cases.  
No dependencies on Spring, JPA, or any external libraries.

**Typical package:**  
`domain.core.<module>`

### ✅ Use Case conventions
- Each use case defines an **input port interface** named after the business action.
- Each interface exposes a single method **`handle(...)`**.

```java
public interface CreateExampleUseCase {
    CreateExampleResult handle(CreateExampleCommand command);
}
```

### ✅ Command / Query / Result structure
| Type | Purpose | Example |
|-------|----------|---------|
| **Command** | Write operations | `CreateExampleCommand` |
| **Query** | Read operations | `GetExampleQuery` |
| **Result** | Output / Return model | `CreateExampleResult` |

### ✅ Domain exceptions
All business errors extend `BaseBusinessException` and reference an enum of `IBusinessError`.

```java
throw new BaseBusinessException(ExampleBusinessError.NAME_ALREADY_TAKEN);
```

---

## 5️⃣ Domain → External (Ports & Adapters)

**Responsibility:**  
Define and implement interfaces for interactions with external systems  
(databases, REST clients, message brokers, files, etc.).

---

### ✅ Output Ports (domain side)
Express **business intentions**, not technical operations.

```java
public interface ExampleRepositoryPort {
    Example register(Example example);
    Optional<Example> load(ExampleId id);
    boolean isNameTaken(ExampleName name);
    List<Example> browse();
    void remove(ExampleId id);
}
```

> ❌ Avoid CRUD or technical verbs (`save`, `findById`, `deleteById`, etc.)
> ✅ Prefer **domain semantics** (`register`, `load`, `isNameTaken`, `browse`, `remove`)

---

### ✅ Adapters (infrastructure side)
Implement ports using the chosen technology (JPA, HTTP, Kafka...).

```java
@Repository
@RequiredArgsConstructor
public class ExampleRepositoryAdapter implements ExampleRepositoryPort {

    private final ExampleJpaRepository jpa;
    private final ExampleMapper mapper;

    @Override
    public Example register(Example example) {
        var entity = mapper.toEntity(example);
        var saved = jpa.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Example> load(ExampleId id) {
        return jpa.findById(id.value()).map(mapper::toDomain);
    }

    @Override
    public boolean isNameTaken(ExampleName name) {
        return jpa.existsByName(name.value());
    }

    @Override
    public List<Example> browse() {
        return jpa.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public void remove(ExampleId id) {
        jpa.deleteById(id.value());
    }
}
```

---

## 6️⃣ Mapping Conventions

**Mappers** translate between domain objects and infrastructure entities.

```java
@Component
public class ExampleMapper {
    public ExampleEntity toEntity(Example d) {
        return new ExampleEntity(d.id().value(), d.name().value());
    }

    public Example toDomain(ExampleEntity e) {
        return new Example(new ExampleId(e.getId()), new ExampleName(e.getName()));
    }
}
```

---

## 7️⃣ Entity & Repository (External Layer)

### JPA Entity
```java
@Entity
@Table(name = "examples")
public class ExampleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;
}
```

### JPA Repository
```java
public interface ExampleJpaRepository extends JpaRepository<ExampleEntity, Long> {
    boolean existsByName(String name);
}
```

---

## 8️⃣ Error Handling & Internationalization

| Concern | Layer | Responsibility |
|----------|--------|----------------|
| **Validation** | Entrypoint | Jakarta `@NotBlank`, `@Size`, etc. |
| **Localization** | Presentation | `MessageSource`, message bundles per module |
| **Business Errors** | Domain | `BaseBusinessException`, `IBusinessError` |
| **HTTP Mapping** | Entrypoint | `GlobalExceptionHandler` |

---

## 9️⃣ Transaction Boundaries

| Layer | Responsible for Transactions |
|--------|------------------------------|
| Entrypoint | ❌ No |
| Presentation | ❌ No |
| Application | ✅ Yes (`@Transactional`) |
| Domain | ❌ No |
| External | ❌ No (relies on Application) |

---

## 🖚 Summary Table

| Direction | Verb | Meaning |
|------------|------|---------|
| Entrypoint → Presentation | `present(...)` | Convert and delegate API request |
| Presentation → Application | `process(...)` | Launch an application workflow |
| Application → Domain | `handle(...)` | Execute a business use case |
| Domain → External | domain verbs (`register`, `load`, `remove`, etc.) | Express business-level persistence intentions |

---

## 💡 Final Principles

1. **The Domain owns the business language.**
2. **Each layer has a single responsibility.**
3. **No cross-layer dependency (except downward direction).**
4. **Adapters can change without touching the domain.**
5. **Method verbs reflect purpose, not implementation.**

> 🧠 *"The domain asks — the adapter executes."*

---

