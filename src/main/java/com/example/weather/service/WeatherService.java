package com.example.weather.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    @Value("${weather.api.key}")
    private String apiKey;

    public String fetchWeatherDataForCity(String city) {
        try {
            String apiUrl = String.format("https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s", city, apiKey);
            System.out.println("Making API call to: " + apiUrl);

            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.getForObject(apiUrl, String.class);
            System.out.println("API response: " + response);

            if (response.isEmpty()) {
                throw new RuntimeException("Received empty response for city: " + city);
            }

            return response;
        } catch (Exception e) {
            System.err.println("Error during API call for city: " + city);
            throw new RuntimeException("Error fetching weather data for city: " + city, e);
        }
    }

}
