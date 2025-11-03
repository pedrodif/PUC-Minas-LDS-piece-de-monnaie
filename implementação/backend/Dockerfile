# Multi-stage build
# Stage 1: Build the application
FROM eclipse-temurin:21-jdk AS builder
LABEL authors="pedro"

WORKDIR /app
COPY pom.xml .
COPY src ./src

# Install Maven and build the application
RUN apt-get update && apt-get install -y maven && \
    mvn clean package -DskipTests

# Stage 2: Run the application
FROM eclipse-temurin:21-jre
LABEL authors="pedro"

WORKDIR /app
COPY --from=builder /app/target/piece-de-monnaie-*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]