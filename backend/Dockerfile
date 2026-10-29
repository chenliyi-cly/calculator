# ---------- Stage 1: build the backend with Maven ----------
# Official Maven + JDK 17 build environment
FROM maven:3.8-openjdk-17 AS builder

# Working directory inside the build container
WORKDIR /app

# Copy pom.xml first to cache the dependency layer
COPY pom.xml .

# Download dependencies
RUN mvn dependency:go-offline

# Copy backend source code
COPY src ./src

# Package into a jar, skip unit tests here (tests run in CI)
RUN mvn clean package -DskipTests

# ---------- Stage 2: lightweight runtime ----------
# JRE only, no compiler needed to run the service
FROM eclipse-temurin:17-jre

WORKDIR /app

# Copy the built jar from the builder stage
COPY --from=builder /app/target/calculator-backend-1.0-SNAPSHOT.jar app.jar

# Backend listens on port 8000
EXPOSE 8000

# Start the HTTP calculator service
ENTRYPOINT ["java", "-jar", "app.jar"]
