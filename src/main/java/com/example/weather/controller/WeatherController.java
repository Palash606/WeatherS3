package com.example.weather.controller;

import com.example.weather.service.S3Service;
import com.example.weather.service.WeatherService;
import com.example.weather.util.WeatherDataExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private S3Service s3Service;

    @GetMapping("/")
    public String displayWeatherData(Model model) {
        try {
            // Step 1: Define cities for which weather data is required
            String[] cities = {"Mumbai", "Delhi", "Bangalore", "Chennai", "Kolkata"};
            List<Map<String, String>> weatherDataList = new ArrayList<>();

            for (String city : cities) {
                // Step 2: Fetch data for each city from the OpenWeather API
                String rawData = weatherService.fetchWeatherDataForCity(city);
                System.out.println("Fetched weather data for " + city + ": " + rawData);

                // Step 3: Upload the data to S3
                String fileName = city + "-weather-data.json";
                s3Service.uploadToS3(fileName, rawData);
                System.out.println("Uploaded weather data for " + city + " to S3");

                // Step 4: Fetch the data back from S3
                String rawWeatherDataFromS3 = s3Service.fetchFromS3(fileName);
                System.out.println("Fetched weather data from S3 for " + city + ": " + rawWeatherDataFromS3);

                // Step 5: Extract specific data
                Map<String, String> weatherData = WeatherDataExtractor.extractSpecificDataAsMap(rawWeatherDataFromS3);

                // Convert temperature from Kelvin to Celsius
                double tempInKelvin = Double.parseDouble(weatherData.get("Temperature"));
                double tempInCelsius = tempInKelvin - 273.15;
                weatherData.put("Temperature", String.format("%.2f °C", tempInCelsius));

                // Add to the weather data list
                weatherDataList.add(weatherData);
            }

            // Step 6: Pass the consolidated weather data to the model
            model.addAttribute("weatherDataList", weatherDataList);

        } catch (Exception e) {
            // Handle errors and pass the error message to the model
            System.out.println("Error processing weather data: " + e.getMessage());
            model.addAttribute("error", "Error processing weather data: " + e.getMessage());
        }
        return "index";
    }
}
