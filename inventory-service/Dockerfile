FROM openjdk:22-ea-28-jdk-slim

COPY target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]