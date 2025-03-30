FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY suppressions.xml .
RUN mvn --no-transfer-progress dependency:go-offline
COPY src src
RUN mvn package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /
COPY --from=build /app/target/*.jar application.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "application.jar"]