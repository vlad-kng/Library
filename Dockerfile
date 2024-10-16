# Use a lightweight base image for Java
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from the target directory to the container
COPY target/library-0.0.1-SNAPSHOT.jar app.jar

# Expose the port that Spring Boot runs on (по умолчанию это 8080)
EXPOSE 8080

# Run the JAR file when the container starts
ENTRYPOINT ["java", "-jar", "app.jar"]