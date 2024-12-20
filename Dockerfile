FROM openjdk:17-jdk-alpine

# Expose the application's port
EXPOSE 8080

# Copy the JAR file into the container
ADD target/weather.jar weather.jar

# Copy the application.properties file to the container
COPY src/main/resources/application.properties /config/application.properties

# Set environment variable to specify Spring configuration locations
ENV SPRING_CONFIG_LOCATION=classpath:/application.properties,file:/config/application.properties

# Run the application
ENTRYPOINT ["java", "-jar", "/weather.jar"]
