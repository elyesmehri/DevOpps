FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/events-project.jar events-project.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/events-project.jar"]
