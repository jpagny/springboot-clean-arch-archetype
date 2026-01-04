# ${artifactId} (generated from clean-arch-archetype)

## Modules
- `${artifactId}-domain`: entities/models, ports, use cases (business rules)
- `${artifactId}-application`: orchestrators/invokers, application services, coordination
- `${artifactId}-transport`: presenters/mappers between API and application (commands/results ↔ DTOs), error & i18n helpers
- `${artifactId}-api`: web controllers and API DTOs (REST endpoints)
- `${artifactId}-infrastructure`: external adapters (JPA/HTTP/Messaging, persistence implementations)
- `${artifactId}-bootstrap`: runnable Spring Boot application (wiring/configuration)

## Run
```bash
mvn -q -DskipTests package
java -jar bootstrap/target/${artifactId}-bootstrap-${version}.jar
