# Stage 1: Build Stage
FROM eclipse-temurin:22-jdk AS build

WORKDIR /app

# Copy Gradle wrapper and build files
COPY gradle /app/gradle
COPY gradlew /app/gradlew
COPY build.gradle /app/build.gradle
COPY settings.gradle /app/settings.gradle

# Run the Gradle bootJar task to build the Spring Boot JAR
RUN ./gradlew clean bootJar --no-daemon

# Copy the entire application source code
COPY . /app

# Run bootJar again to ensure the JAR is built correctly after copying source
RUN ./gradlew bootJar --no-daemon

# Stage 2: Runtime Stage (Smaller Alpine Image)
FROM eclipse-temurin:22-jdk-alpine

WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/build/libs/payment-producer-0.0.1-SNAPSHOT.jar /app/payment-producer.jar

# Command to run the application
CMD ["java", "-jar", "/app/payment-producer.jar"]
