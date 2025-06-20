FROM gradle:8.7.0-jdk17 AS build

WORKDIR /app
COPY --chown=gradle:gradle . .

RUN gradle bootJar --no-daemon

FROM openjdk:17-jdk-slim AS runtime

WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "/app/app.jar"]