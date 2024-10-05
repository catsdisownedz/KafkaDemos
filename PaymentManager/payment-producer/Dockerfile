
FROM eclipse-temurin:22-jdk

WORKDIR /app

COPY gradle /app/gradle
COPY gradlew /app/gradlew
COPY build.gradle /app/build.gradle
COPY settings.gradle /app/settings.gradle
RUN ./gradlew build --no-daemon --offline
COPY . /app

RUN ./gradlew build --no-daemon
CMD ["java", "-jar", "/app/build/libs/payment-consumer-0.0.1-SNAPSHOT.jar"]
