FROM openjdk:17-jdk-alpine
EXPOSE 8080
ADD target/weather.jar weather.jar
ENTRYPOINT ["java","-jar","/weather.jar"]
