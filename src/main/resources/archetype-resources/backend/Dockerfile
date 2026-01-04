# ---- Build stage ----
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /workspace

# Copy POM files first to leverage Docker layer caching
COPY pom.xml ./
COPY backend/pom.xml backend/pom.xml
COPY backend/api/pom.xml backend/api/pom.xml
COPY backend/application/pom.xml backend/application/pom.xml
COPY backend/bootstrap/pom.xml backend/bootstrap/pom.xml
COPY backend/domain/pom.xml backend/domain/pom.xml
COPY backend/external/pom.xml backend/external/pom.xml
COPY backend/transport/pom.xml backend/transport/pom.xml

# Pre-download dependencies (Docker cache optimization)
RUN mvn -B -q -DskipTests dependency:go-offline

# Copy application source code
COPY backend backend

# Build the executable module (adjust -pl if needed)
RUN mvn -B -DskipTests -pl backend/bootstrap -am package

# ---- Runtime stage ----
FROM eclipse-temurin:21-jre
WORKDIR /app

# Basic security: run as non-root user
RUN useradd -r -u 10001 appuser
USER appuser

# Copy the built JAR
# (Wildcard used to avoid hardcoding the artifact name)
COPY --from=build /workspace/backend/bootstrap/target/*.jar /app/app.jar

EXPOSE 8080

# JVM options can be overridden at runtime
ENV JAVA_OPTS=""

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]
