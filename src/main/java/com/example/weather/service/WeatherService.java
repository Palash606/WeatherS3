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
            RestTemplate restTemplate = new RestTemplate();
            return restTemplate.getForObject(apiUrl, String.class);
        } catch (Exception e) {
            throw new RuntimeException("Error fetching weather data for city: " + city, e);
        }
    }
}
