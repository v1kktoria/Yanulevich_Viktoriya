FROM openjdk:17-jdk-slim AS build

WORKDIR /app

COPY pom.xml .
COPY core/ /app/core/
COPY web/ /app/web/
COPY method-execution-time-starter/ /app/method-execution-time-starter/

RUN apt-get update && apt-get install -y maven
RUN mvn dependency:go-offline

RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=build /app/web/target/web-1.0-SNAPSHOT.jar /app/web-1.0-SNAPSHOT.jar

EXPOSE 8080

CMD ["java", "-jar", "web-1.0-SNAPSHOT.jar"]