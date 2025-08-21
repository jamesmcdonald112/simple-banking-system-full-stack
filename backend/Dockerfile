# syntax=docker/dockerfile:1

# ---- Build stage ----
FROM gradle:8.8-jdk21-alpine AS build
WORKDIR /app

# Copy only the build files first to leverage Docker layer caching
COPY build.gradle settings.gradle ./
COPY gradle gradle
COPY gradlew ./
# If you don't have the Gradle wrapper in repo, you can remove the 3 lines above
# and just use the gradle image's 'gradle' CLI directly below.

# Now copy source and build the fat jar
COPY src ./src
# Use wrapper if present; otherwise replace with:  RUN gradle --no-daemon clean bootJar
RUN ./gradlew --no-daemon clean bootJar

# ---- Run stage ----
FROM eclipse-temurin:21-jre
WORKDIR /app

# Render sets PORT; pass it through to Spring Boot
ENV PORT=8080

# Copy the built jar from the previous stage
COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080
CMD ["sh","-c","java -jar app.jar --server.port=${PORT}"]