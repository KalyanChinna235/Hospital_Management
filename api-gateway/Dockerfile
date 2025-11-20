# --------------------------
# Stage 1: Build the application
# --------------------------
FROM maven:3.9.9-eclipse-temurin-21 AS builder
WORKDIR /app

# Copy only the pom.xml first to leverage Docker caching
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Now copy the full source and build
COPY src ./src
RUN mvn clean package -DskipTests

# --------------------------
# Stage 2: Run the application
# --------------------------
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Copy the jar file from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Expose the application port
EXPOSE 4004

# Run the Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar"]
