# Weather Application with S3 Integration

This project fetches weather data from the OpenWeather API, stores it in an AWS S3 bucket, and displays it through a
Spring Boot application.

---

## Features

- Fetches live weather data for multiple cities.
- Stores weather data in AWS S3.
- Retrieves and processes weather data from S3.
- Displays weather data in a user-friendly format.

---

## Prerequisites

1. **Clone the repository:**
   ```bash
   git clone https://github.com/Palash606/WeatherS3.git
   cd WeatherS3

2. **Setup AWS S3 Bucket:**

- Create a new S3 bucket in your AWS account.
- Generate Access and Secret keys for AWS.

3. **Generate an OpenWeather API key**

- Create an account on https://openweathermap.org/api.
- Generate a free api key.

4. **Setup up environment variables on your IDE:**

- WEATHER_API_KEY=your-weather-api-key
- AWS_BUCKET=your-aws-bucket-name
- AWS_REGION=your-aws-region
- AWS_ACCESS_KEY=your-aws-access-key
- AWS_SECRET_KEY=your-aws-secret-key


---

## Docker Setup
- Create a DockerFile: The Dockerfile is already present in the repository.
- Build and push Docker image:
   ```bash
   docker build -t your-dockerhub-username/weather_s3 .
   docker tag your-dockerhub-username/weather_s3:latest your-dockerhub-username/weather_s3:latest
   docker push your-dockerhub-username/weather_s3:latest

- Run the application using Docker:
   ```bash
   docker run -it -p 8000:8080 \
  -e WEATHER_API_KEY=your-weather-api-key \
  -e AWS_BUCKET=your-aws-bucket-name \
  -e AWS_REGION=your-aws-region \
  -e AWS_ACCESS_KEY=your-aws-access-key \
  -e AWS_SECRET_KEY=your-aws-secret-key \
  --name weather-app your-dockerhub-username/weather_s3:latest

- Access the application: Open your browser and navigate to http://localhost:8000.

---

## GitHub Actions Workflow
- This repository includes a GitHub Actions workflow for CI/CD. The workflow builds and pushes the Docker image to Docker Hub on every push to the master branch. The YAML configuration file can be found in the .github/workflows directory.

## Additional Notes
- Ensure that your AWS keys and API keys are kept secure and are not hardcoded in the codebase.
- If any errors occur during deployment or setup, ensure that all environment variables are correctly configured.
