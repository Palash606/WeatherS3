FROM openjdk:17-jdk-alpine
EXPOSE 8080

# Copy the jar file
ADD target/weather.jar weather.jar

# Copy application.properties from the local project to the Docker image
# If you are using application.yml, copy that instead
COPY src/main/resources/application.properties /app/config/application.properties

# Set the working directory to the location where the application.properties file is copied
WORKDIR /app

# Run the jar file, Spring Boot will look for the application.properties file in the working directory
ENTRYPOINT ["java", "-jar", "/app/weather.jar"]
