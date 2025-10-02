# ${artifactId} (generated from clean-arch-archetype)

## Modules
- `${artifactId}-domain`: entités, ports, use-cases
- `${artifactId}-application`: mappers, invokers (orchestrateurs)
- `${artifactId}-api`: contrôleurs web et DTO
- `${artifactId}-infrastructure`: adapters JPA/HTTP/Messaging
- `${artifactId}-bootstrap`: application Spring Boot runnable

## Démarrer
```bash
mvn -q -DskipTests package
java -jar bootstrap/target/${artifactId}-bootstrap-${version}.jar
curl http://localhost:8080/health
```
