package com.example.weather;

import com.example.weather.service.S3Service;
import com.example.weather.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class WeatherScheduler {

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private S3Service s3Service;

    @Scheduled(cron = "*/5 * * * *") // Every 5 min
    public void fetchAndStoreWeatherData() {
        String[] cities = {"Mumbai", "Delhi", "Bangalore", "Chennai", "Kolkata"};

        for (String city : cities) {
            try {
                // Fetch data for each city from OpenWeather API
                String rawData = weatherService.fetchWeatherDataForCity(city);
                System.out.println("Fetched weather data for " + city + ": " + rawData);

                // Upload the weather data to S3
                String fileName = city + "-weather-data.json";
                s3Service.uploadToS3(fileName, rawData);
                System.out.println("Uploaded weather data for " + city + " to S3");
            } catch (Exception e) {
                System.err.println("Error processing weather data for " + city + ": " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
