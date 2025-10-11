# 🏗️ Architecture Overview

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
│        └─ impl/                   # Optional: implementations of use cases
│
├─ presentation/
│  ├─ pom.xml
│  ├─ src/main/java/com/example/presentation/
│  │  ├─ common/
│  │  │  ├─ configuration/
│  │  │  │  └─ MessageResourceConfiguration.java
│  │  │  ├─ i18n/
│  │  │  │  ├─ MessageResolver.java
│  │  │  │  └─ impl/
│  │  │  │     └─ MessageResolverImpl.java
│  │  │  └─ qualifier/
│  │  │     └─ PresentationConverter.java
│  │  ├─ config/
│  │  │  └─ ModelMapperPresentationConfig.java  # Bean "presentationMapper" + converter registration
│  │  └─ representations/
│  │     └─ example/
│  │        ├─ input/
│  │        │  ├─ requests/                      # Input DTOs (used by entrypoint)
│  │        │  │  └─ CreateExampleRequest.java
│  │        │  ├─ converters/
│  │        │  │  └─ CreateExampleRequestToCommandConverter.java
│  │        │  └─ presenters/
│  │        │     └─ CreateExampleInputPresenter.java
│  │        ├─ output/
│  │        │  ├─ responses/                     # Output DTOs
│  │        │  │  └─ CreateExampleResponse.java
│  │        │  ├─ converters/
│  │        │  │  └─ CreateExampleResultToResponseConverter.java
│  │        │  └─ presenters/
│  │        │     └─ CreateExampleOutputPresenter.java
│  │        └─ facade/
│  │           └─ ExampleEndpointFacade.java     # Presentation orchestration layer
│  └─ src/main/resources/
│     └─ i18n/
│        ├─ example.properties
│        ├─ example_fr.properties
│        └─ errors.properties                    # Optional
│
├─ external/
│  ├─ pom.xml
│  └─ src/main/java/com/example/external/
│     ├─ persistence/
│     │  ├─ entity/                              # JPA Entities
│     │  └─ adapter/                             # Implements ExampleRepositoryPort (@Repository)
│     ├─ httpclient/                             # REST clients (outbound)
│     └─ messaging/                              # Kafka / RabbitMQ adapters
│
├─ entrypoint/
│  ├─ pom.xml
│  └─ src/main/java/com/example/entrypoint/
│     ├─ rest/
│     │  ├─ endpoints/example/controller/
│     │  │  └─ ExampleController.java            # Uses presentation facade + DTOs
│     │  └─ advice/                              # @ControllerAdvice for error handling
│     └─ config/
│        └─ I18nWebConfig.java                   # AcceptHeaderLocaleResolver (web locale)
│  └─ src/main/resources/
│     └─ logback-spring.xml
│
└─ bootstrap/
   ├─ pom.xml
   └─ src/main/java/com/example/bootstrap/
      ├─ Application.java                         # @SpringBootApplication(scanBasePackages="com.example")
      └─ configuration/
         └─ ModelMapperConfiguration.java         # Global mappers (e.g., externalMapper)
```

---

## ⚙️ Module Responsibilities

| Module | Responsibility |
|---------|----------------|
| **domain** | Pure business logic: entities, value objects, domain services, commands, queries, and ports. No framework dependencies. |
| **application** | Application services and use case orchestration. Invokes domain logic and ports (in/out). Framework-agnostic. |
| **presentation** | Converts data between entrypoint DTOs and domain commands/results. Hosts ModelMapper config, presenters, and i18n logic. |
| **external** | Outbound adapters (database, HTTP clients, messaging). Implements domain output ports. |
| **entrypoint** | Inbound adapters (REST controllers, request validation, exception handling). Delegates to presentation layer. |
| **bootstrap** | Spring Boot runtime layer: starts the app, wires modules, defines filters, and logging setup. |
