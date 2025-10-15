# 🏷️ Architecture Overview

This project implements a **Clean Architecture** using Spring Boot with a **multi-module structure**.  
The goal is to keep business rules independent of frameworks and I/O, and to isolate infrastructure, wiring, and presentation logic cleanly.

---

## 🧩 Project Layout

```
backend/
├─ pom.xml                          # Parent POM (packaging = pom)
├─ domain/
│  ├─ pom.xml
│  └─ src/main/java/com/example/domain/
│     └─ core/example/
│        ├─ model/                  # Entities / Value Objects
│        ├─ operations/
│        │  ├─ commands/            # CreateExampleCommand, ...
│        │  ├─ results/             # CreateExampleResult, ...
│        │  └─ queries/             # GetExampleQuery
│        └─ ports/
│           ├─ input/               # Use case interfaces (inbound)
│           └─ output/              # ExampleRepositoryPort, ...
│
├─ application/
│  ├─ pom.xml
│  └─ src/main/java/com/example/application/
│     └─ services/example/
│        ├─ ExampleApplicationService.java
│        └─ impl/                   # Optional: use case implementations
│
├─ presentation/
│  ├─ pom.xml
│  ├─ src/main/java/com/example/presentation/
│  │  ├─ common/
│  │  │  ├─ configuration/
│  │  │  │  └─ MessageResourceConfiguration.java   # Loads i18n bundles dynamically
│  │  │  ├─ errors/
│  │  │  │  ├─ DefaultErrorResponse.java           # Standardized error response DTO
│  │  │  │  ├─ http/
│  │  │  │  │  ├─ ErrorCodeToHttpStatusResolver.java
│  │  │  │  │  └─ impl/DefaultErrorCodeToHttpStatusResolverImpl.java
│  │  │  │  └─ resolver/
│  │  │     ├─ BusinessErrorMessageResolver.java
│  │  │     └─ impl/BusinessErrorMessageResolverImpl.java
│  │  ├─ i18n/
│  │  │  ├─ MessageResolver.java
│  │  │  └─ impl/MessageResolverImpl.java
│  │  └─ qualifier/
│  │     └─ PresentationConverter.java
│  │
│  │  ├─ config/
│  │  │  └─ ModelMapperPresentationConfig.java     # Bean "presentationMapper" + converter registration
│  │
│  │  └─ representations/
│  │     └─ example/
│  │        ├─ input/
│  │        │  ├─ requests/                        # Input DTOs (used by entrypoint)
│  │        │  │  └─ CreateExampleRequest.java
│  │        │  ├─ converters/
│  │        │  │  └─ CreateExampleRequestToCommandConverter.java
│  │        │  └─ presenters/
│  │        │     └─ CreateExampleInputPresenter.java
│  │        ├─ output/
│  │        │  ├─ responses/                       # Output DTOs
│  │        │  │  └─ CreateExampleResponse.java
│  │        │  ├─ converters/
│  │        │  │  └─ CreateExampleResultToResponseConverter.java
│  │        │  └─ presenters/
│  │        │     └─ CreateExampleOutputPresenter.java
│  │        └─ facade/
│  │           └─ ExampleEndpointFacade.java       # Presentation orchestration layer
│  │
│  └─ src/main/resources/
│     └─ i18n/
│        ├─ example.properties
│        ├─ example_fr.properties
│        ├─ global_errors.properties
│        └─ global_errors_fr.properties
│
├─ external/
│  ├─ pom.xml
│  └─ src/main/java/com/example/external/
│     ├─ persistence/
│     │  ├─ entity/                                # JPA Entities
│     │  └─ adapter/                               # Implements ExampleRepositoryPort (@Repository)
│     ├─ httpclient/                               # REST clients (outbound)
│     └─ messaging/                                # Kafka / RabbitMQ adapters
│
├─ entrypoint/
│  ├─ pom.xml
│  └─ src/main/java/com/example/entrypoint/
│     ├─ rest/
│     │  ├─ endpoints/example/controller/
│     │  │  └─ ExampleController.java              # Uses presentation facade + DTOs
│     │  └─ advice/
│     │     └─ GlobalExceptionHandler.java         # Delegates to resolvers in presentation
│     └─ config/
│        └─ I18nWebConfig.java                     # AcceptHeaderLocaleResolver (web locale)
│
│  └─ src/main/resources/
│     └─ logback-spring.xml
│
└─ bootstrap/
   ├─ pom.xml
   └─ src/main/java/com/example/bootstrap/
      ├─ Application.java                           # @SpringBootApplication(scanBasePackages="com.example")
      └─ configuration/
         └─ ModelMapperConfiguration.java           # Global mappers (e.g., externalMapper)
```

---

## ⚙️ Module Responsibilities

| Module | Responsibility |
|---------|----------------|
| **domain** | Pure business logic — entities, value objects, domain services, commands, queries, and ports. No dependency on frameworks. |
| **application** | Orchestrates use cases. Calls domain ports and aggregates business workflows. Framework-agnostic. |
| **presentation** | Contains DTO converters, presenters, i18n handling, and all error/message resolvers. Translates between domain and entrypoint. |
| **external** | Outbound adapters (database, REST, messaging). Implements domain output ports. |
| **entrypoint** | Inbound adapters (REST controllers, validation, HTTP exposure). Delegates logic to presentation. Contains only wiring and exception handler facade. |
| **bootstrap** | Application runtime configuration. Starts Spring Boot, configures base packages, and initializes cross-module beans (e.g., ModelMapper, logging). |