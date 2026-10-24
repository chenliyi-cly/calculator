# ---------- Stage 1: Build the project with Maven ----------
FROM maven:3.8-openjdk-17 AS builder
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

# ---------- Stage 2: Runtime image ----------
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=builder /app/target/calculator-1.0-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
