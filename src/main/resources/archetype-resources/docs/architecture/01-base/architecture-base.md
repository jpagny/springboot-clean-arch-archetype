# Base Architecture (Spring Boot Clean Architecture Archetype)

This document explains the baseline architecture scaffolded by this archetype. It describes the layers, modules, directories, and the way responsibilities are divided and wired together.

The archetype promotes Clean Architecture principles: domain-centric design, clear separation of concerns, dependency direction toward the domain, and interfaces (ports) between layers.

## Goals
- Keep the domain model independent of frameworks and infrastructure.
- Encapsulate business use cases behind explicit input/output ports.
- Decouple transport (REST, messaging, etc.) from application and domain.
- Make external/infrastructure concerns replaceable (persistence, integrations).
- Provide consistent cross-cutting concerns (errors, i18n, logging, correlation, config).

---

## Modules (Multi-Module Maven Project)

Backend parent POM declares the following modules (dependency direction points upward to `domain`):

1. domain
   - Pure business domain, use cases, ports, and domain utilities (exceptions, pagination, CRUD helpers).
   - Has no dependency on Spring or external libraries (beyond minimal utility-only, if any).
2. application
   - Application services that orchestrate domain use cases and interaction between layers when needed.
   - Contains service interfaces/implementations; thin layer keeping use cases cohesive.
3. transport
   - Presentation/transport layer abstractions and implementations (e.g., presenters, endpoint handlers, i18n, error mapping).
   - Converts transport requests to domain commands and domain results to transport responses.
4. external
   - Infrastructure adapters (e.g., persistence). Implements domain output ports using concrete tech (JPA, etc.).
   - Contains mappers and adapters to translate domain models to persistence models and vice versa.
5. api
   - Edge adapters for specific protocols (e.g., REST controllers) and global Spring MVC advice.
   - Delegates to transport endpoint handlers and presenters.
6. bootstrap
   - Spring Boot application entry point, configurations, wiring, and cross-cutting filters.
   - Holds application.yml profiles and logging configuration.

The parent aggregator under project root references `backend` as a module; `backend/pom.xml` manages versions (Java 21, Lombok, Jacoco, Sonar) and plugin management.

---

## Clean Architecture Layers and Dependency Rules

- Domain (center) defines core business logic and ports, with no dependency on other layers.
- Use cases expose input ports (driven by transport/API) and depend on output ports (implemented by external adapters).
- Transport/API depend inward (domain + application) to invoke use cases and present responses.
- External adapters depend inward to implement domain output ports.
- Bootstrap depends on all others to wire and run the app.

High-level dependency flow:

```
[api] ─┐
        │   calls via handlers/presenters
[transport] ───▶ [application] ───▶ [domain] ◀─── [external]
                                           ▲
                                           │ implementations of output ports
[bootstrap] wires everything and starts Spring Boot
```

---

## Directory Structure (selected highlights)

- backend/domain
  - commons
    - crud/modules and impl: base classes to implement CRUD use cases (create, update, list, getById, delete).
    - ports/output: `CommandRepositoryPort` and `QueryRepositoryPort` — abstractions for write/read persistence operations used by domain.
    - exceptions: error codes and business exception model (`BaseBusinessException`, `IBusinessError`, `ErrorContext`, etc.).
    - pagination: simple `Page`, `PageRequest`, `Sort` types independent of Spring.
  - core/example
    - operations/commands: domain commands (e.g., `CreateExampleCommand`).
    - operations/results: domain results (e.g., `CreateExampleResult`).
    - ports/input: use case interfaces (e.g., `CreateExampleUseCase`).
    - usecases: use case implementations (e.g., `CreateExampleUseCaseImpl`).

- backend/application
  - services/example: service API and implementation that coordinate use cases (e.g., `ExampleService`, `ExampleServiceImpl`).

- backend/transport
  - common/configuration: message sourcing config for i18n (`MessageResourceConfiguration`).
  - common/contracts: generic `EndpointHandler`, `InputPresenter`, `OutputPresenter` interfaces.
  - common/errors: `DefaultErrorResponse` and HTTP status mapping (`ErrorCodeToHttpStatusResolver`).
  - common/i18n: `MessageResolver`, `BusinessErrorMessageResolver` + impls; resources under `transport/src/main/resources/i18n`.
  - endpoints/example/create
    - input: request DTOs (e.g., `CreateExampleRequest`).
    - output: presenters and response DTOs (e.g., `CreateExampleResponse`, `CreateExampleResultToResponsePresenter`).
    - resolver: endpoint-specific message resolvers.

- backend/external
  - common/persistence/adapters: base JPA adapters (e.g., `AbstractJpaCommandRepositoryAdapter`, `AbstractJpaQueryRepositoryAdapter`).
  - common/persistence/mappers: mapping interfaces (`PersistenceMapper`, `IdMapper`, `SpringSortMapper`) and implementations (e.g., `LongIdMapper`).

- backend/api
  - rest/advice: `GlobalExceptionHandler` for API-level error handling mapping domain errors to HTTP.
  - rest/endpoints: example `ExampleController` that delegates to transport endpoint handler/presenter pipeline.

- backend/bootstrap
  - Application.java: Spring Boot entry point.
  - configuration:
    - `CacheConfiguration`, `CorrelationIdConfiguration`, `RepositoryConfiguration`, `ServiceDomainConfiguration`, `UseCaseConfiguration` — central wiring of beans, adapters, and presenters.
  - web/filter: `CorrelationIdFilter` for request tracing.
  - resources: `application.yml` + profile overrides (`application-local.yml`, `application-dev.yml`, `application-prod.yml`), and `logback-spring.xml`.

- docs
  - api/openapi.yml: OpenAPI definition starter.
  - architecture
    - 01-base: this document and a short `README.md`.
    - 02-application: application-level documentation placeholder.

- http
  - envs: IntelliJ HTTP client environment files.
  - requests: sample HTTP requests (actuator, example feature).

## Full Directory Tree (archetype resources)

```text
archetype-resources/
├─ backend/
│  ├─ Dockerfile
│  ├─ pom.xml                      # backend parent POM
│  ├─ sonar-project.yml
│  ├─ api/
│  │  ├─ pom.xml
│  │  └─ src/main/java/__packageInPathFormat__/api/rest/
│  │     ├─ advice/
│  │     │  └─ GlobalExceptionHandler.java
│  │     └─ endpoints/
│  │        └─ ExampleController.java
│  ├─ application/
│  │  ├─ pom.xml
│  │  └─ src/main/java/__packageInPathFormat__/application/services/example/
│  │     ├─ ExampleService.java
│  │     └─ impl/
│  │        └─ ExampleServiceImpl.java
│  ├─ bootstrap/
│  │  ├─ pom.xml
│  │  ├─ src/main/java/__packageInPathFormat__/bootstrap/
│  │  │  ├─ Application.java
│  │  │  ├─ configuration/
│  │  │  │  ├─ CacheConfiguration.java
│  │  │  │  ├─ CorrelationIdConfiguration.java
│  │  │  │  ├─ RepositoryConfiguration.java
│  │  │  │  ├─ ServiceDomainConfiguration.java
│  │  │  │  └─ UseCaseConfiguration.java
│  │  │  └─ web/filter/
│  │  │     └─ CorrelationIdFilter.java
│  │  └─ src/main/resources/
│  │     ├─ application.yml
│  │     ├─ application-local.yml
│  │     ├─ application-dev.yml
│  │     ├─ application-prod.yml
│  │     └─ logback-spring.xml
│  ├─ domain/
│  │  ├─ pom.xml
│  │  └─ src/main/java/__packageInPathFormat__/domain/
│  │     ├─ commons/
│  │     │  ├─ crud/modules/
│  │     │  │  ├─ CrudModules.java
│  │     │  │  └─ impl/
│  │     │  │     ├─ AbstractCreateModule.java
│  │     │  │     ├─ AbstractDeleteModule.java
│  │     │  │     ├─ AbstractGetByIdModule.java
│  │     │  │     ├─ AbstractListModule.java
│  │     │  │     └─ AbstractUpdateModule.java
│  │     │  ├─ ports/output/
│  │     │  │  ├─ CommandRepositoryPort.java
│  │     │  │  └─ QueryRepositoryPort.java
│  │     │  ├─ exceptions/
│  │     │  │  ├─ codes/ErrorCode.java
│  │     │  │  └─ engine/
│  │     │  │     ├─ BaseBusinessException.java
│  │     │  │     ├─ ErrorContext.java
│  │     │  │     ├─ IBaseException.java
│  │     │  │     └─ IBusinessError.java
│  │     │  └─ pagination/
│  │     │     ├─ Page.java
│  │     │     ├─ PageRequest.java
│  │     │     └─ Sort.java
│  │     └─ core/example/
│  │        ├─ exceptions/
│  │        │  ├─ ExampleBusinessError.java
│  │        │  └─ ExampleDomainException.java
│  │        ├─ messages/
│  │        │  └─ ExampleMessageKey.java
│  │        ├─ operations/
│  │        │  ├─ commands/
│  │        │  │  └─ CreateExampleCommand.java
│  │        │  └─ results/
│  │        │     └─ CreateExampleResult.java
│  │        ├─ ports/input/
│  │        │  └─ CreateExampleUseCase.java
│  │        └─ usecases/
│  │           └─ CreateExampleUseCaseImpl.java
│  ├─ external/
│  │  ├─ pom.xml
│  │  └─ src/main/java/__packageInPathFormat__/external/common/
│  │     ├─ persistence/adapters/
│  │     │  ├─ AbstractJpaCommandRepositoryAdapter.java
│  │     │  ├─ AbstractJpaQueryRepositoryAdapter.java
│  │     │  └─ DefaultSpringSortMapper.java
│  │     └─ persistence/mappers/
│  │        ├─ IdMapper.java
│  │        ├─ LongIdMapper.java
│  │        ├─ PersistenceMapper.java
│  │        └─ SpringSortMapper.java
│  └─ transport/
│     ├─ pom.xml
│     ├─ src/main/java/__packageInPathFormat__/transport/common/
│     │  ├─ configuration/MessageResourceConfiguration.java
│     │  ├─ contracts/
│     │  │  ├─ EndpointHandler.java
│     │  │  ├─ InputPresenter.java
│     │  │  └─ OutputPresenter.java
│     │  ├─ errors/
│     │  │  ├─ DefaultErrorResponse.java
│     │  │  └─ http/
│     │  │     ├─ ErrorCodeToHttpStatusResolver.java
│     │  │     └─ impl/DefaultErrorCodeToHttpStatusResolverImpl.java
│     │  ├─ i18n/
│     │  │  ├─ BusinessErrorMessageResolver.java
│     │  │  ├─ MessageResolver.java
│     │  │  └─ impl/
│     │  │     ├─ BusinessErrorMessageResolverImpl.java
│     │  │     └─ MessageResolverImpl.java
│     │  └─ endpoints/example/create/
│     │     ├─ ExampleCreateEndpointHandler.java
│     │     ├─ input/
│     │     │  └─ CreateExampleRequest.java
│     │     └─ output/
│     │        ├─ CreateExampleResponse.java
│     │        └─ CreateExampleResultToResponsePresenter.java
│     └─ src/main/resources/i18n/
│        ├─ example.properties
│        ├─ example_fr.properties
│        ├─ global_errors.properties
│        └─ global_errors_fr.properties
├─ docs/
│  ├─ api/
│  │  └─ openapi.yml
│  └─ architecture/
│     ├─ 01-base/
│     │  ├─ README.md
│     │  └─ architecture-base.md
│     └─ 02-application/
│        └─ README.md
├─ http/
│  ├─ envs/
│  │  ├─ http-client.env.json
│  │  └─ http-client.private.env.json
│  └─ requests/
│     ├─ actuator.http
│     └─ example.http
├─ pom.xml                         # aggregator parent of backend
└─ project-root/
   ├─ README.md
   └─ docker-compose.yml
```

---

## Example Flow: Create Example

1. API layer (`api/rest/endpoints/ExampleController`) receives a REST request.
2. Delegates to a transport endpoint handler (`transport/.../ExampleCreateEndpointHandler`).
3. The endpoint handler builds a `CreateExampleCommand` directly from `CreateExampleRequest`.
4. Handler invokes the domain `CreateExampleUseCase` (from `domain/core/example/usecases`).
5. Use case may call output ports (e.g., `CommandRepositoryPort`, `QueryRepositoryPort`) which are implemented by external adapters (e.g., JPA adapters) configured in bootstrap.
6. Result (`CreateExampleResult`) is returned to transport.
7. Output presenter maps result into `CreateExampleResponse` DTO.
8. API layer returns HTTP response; errors are mapped by `GlobalExceptionHandler` with HTTP status resolved from domain error codes.

---

## Cross-Cutting Concerns

- Error Handling
  - Domain-level errors represented by `IBusinessError` and `BaseBusinessException` with `ErrorContext`.
  - Transport/API map errors to `DefaultErrorResponse` and HTTP status via `ErrorCodeToHttpStatusResolver`.

- Internationalization (i18n)
  - Message bundles located in `transport/src/main/resources/i18n` (e.g., `example.properties`, `global_errors.properties` and locale variants).
  - `MessageResourceConfiguration` wires message sources; resolvers provide domain/business-aware messages.

- Mapping
  - Explicit and simple mappings: handlers build commands from requests; output presenters build response DTOs from domain results.
  - Persistence adapters use dedicated mapper interfaces (e.g., `PersistenceMapper`, `IdMapper`, `SpringSortMapper`).

- Correlation/Tracing
  - `CorrelationIdFilter` adds/propagates a correlation id across requests for logging and tracing.

- Caching
  - `CacheConfiguration` centralizes Spring Cache setup if/when used by use cases or repositories.

- Configuration and Profiles
  - `application.yml` defines defaults; `application-local.yml`, `application-dev.yml`, `application-prod.yml` override environment-specific values.
  - `logback-spring.xml` configures logging format/levels.

---

## Build, Quality, and Runtime

- Java 21, Spring Boot parent 3.5.x.
- Lombok is available via parent dependency management.
- Plugins managed in parent POM: compiler, surefire/failsafe (unit/integration tests), Jacoco, Sonar, Spring Boot plugin, Maven Enforcer (Java version).
- Docker: `backend/Dockerfile` provides a starter Docker build for the application module.

---

## How to Add a New Feature (Guideline)

1. Domain
   - Define command(s), result(s) under `domain/core/<feature>/operations`.
   - Declare input port(s) under `domain/core/<feature>/ports/input`.
   - Implement use case(s) under `domain/core/<feature>/usecases`, depending only on ports and domain types.
   - If persistence or external IO is required, add/extend output ports in `domain/commons/ports/output` or feature-specific output ports.

2. External
   - Implement output ports with concrete adapters (e.g., JPA repositories) under `external/...` and provide mappers.

3. Transport/API
   - Add endpoint handler and presenters under `transport/endpoints/<feature>/...`.
   - Create request/response DTOs; in handlers, build domain commands from requests; presenters produce response DTOs from domain results.
   - In `api`, expose a controller (REST or other protocol) delegating to the transport handler/presenter.

4. Bootstrap
   - Register beans in configuration classes (use case implementations, adapters, mappers, presenters, handlers).

5. Docs & Tests
   - Update OpenAPI (`docs/api/openapi.yml`) and add HTTP client samples under `http/requests`.
   - Write unit tests for use cases, adapter tests for external, and integration tests as needed.

---

## Conventions

- The domain layer must not depend on Spring or any web/persistence framework.
- Use descriptive names for ports: `...Port` for interfaces in domain; adapters implement them in `external`.
- Keep controllers thin: all logic should be in handlers/presenters and use cases.
- Keep mappers small and explicit; prefer composition and unit tests for mapping.
- Respect package visibility when helpful to keep internals hidden.

---

## References (in repository)

- Parent POM: `src/main/resources/archetype-resources/backend/pom.xml`
- Example Controller: `.../backend/api/src/main/java/__packageInPathFormat__/api/rest/endpoints/ExampleController.java`
- Global Exception Handler: `.../backend/api/src/main/java/__packageInPathFormat__/api/rest/advice/GlobalExceptionHandler.java`
- Use Case Interface: `.../backend/domain/.../core/example/ports/input/CreateExampleUseCase.java`
- Use Case Impl: `.../backend/domain/.../core/example/usecases/CreateExampleUseCaseImpl.java`
- Transport Handler: `.../backend/transport/.../endpoints/example/create/ExampleCreateEndpointHandler.java`
- Input/Output Presenters and DTOs: `.../backend/transport/.../endpoints/example/create/...`
- External JPA Adapter Bases: `.../backend/external/.../persistence/adapters/AbstractJpaCommandRepositoryAdapter.java` and `.../backend/external/.../persistence/adapters/AbstractJpaQueryRepositoryAdapter.java`
- Bootstrap Application: `.../backend/bootstrap/src/main/java/__packageInPathFormat__/bootstrap/Application.java`
- Configurations: `.../backend/bootstrap/src/main/java/__packageInPathFormat__/bootstrap/configuration/*.java`
- Correlation Filter: `.../backend/bootstrap/src/main/java/__packageInPathFormat__/bootstrap/web/filter/CorrelationIdFilter.java`
- OpenAPI: `src/main/resources/archetype-resources/docs/api/openapi.yml`

---

This base architecture document is generated by the archetype to guide teams in structuring new features consistently while keeping business logic at the center.