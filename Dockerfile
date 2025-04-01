FROM openjdk:17-jdk-slim
WORKDIR /app
EXPOSE 8082
COPY target/*.jar /app/event-service-0.0.1-SNAPSHOT.jar
CMD ["java", "-jar", "/app/event-service-0.0.1-SNAPSHOT.jar"]