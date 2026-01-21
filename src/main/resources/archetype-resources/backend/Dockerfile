# ---- Build stage ----
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /workspace

# Cache Maven: POMs
COPY pom.xml ./
COPY api/pom.xml api/pom.xml
COPY application/pom.xml application/pom.xml
COPY bootstrap/pom.xml bootstrap/pom.xml
COPY domain/pom.xml domain/pom.xml
COPY external/pom.xml external/pom.xml
COPY transport/pom.xml transport/pom.xml

RUN mvn -B -q -DskipTests dependency:go-offline

# Sources
COPY . .

# Build
RUN mvn -B -DskipTests -pl bootstrap -am package

# ---- Runtime stage ----
FROM eclipse-temurin:21-jre
WORKDIR /app

RUN useradd -r -u 10001 appuser
USER appuser

COPY --from=build /workspace/bootstrap/target/*.jar /app/app.jar

EXPOSE 8080
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]
