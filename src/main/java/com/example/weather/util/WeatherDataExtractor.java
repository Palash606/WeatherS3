package com.example.weather.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

public class WeatherDataExtractor {

    public static Map<String, String> extractSpecificDataAsMap(String rawWeatherData) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(rawWeatherData);

        Map<String, String> weatherDataMap = new HashMap<>();
        weatherDataMap.put("City", getSafeText(rootNode, "name").toUpperCase());
        weatherDataMap.put("Temperature", getSafeText(rootNode.get("main"), "temp"));
        weatherDataMap.put("Humidity", getSafeText(rootNode.get("main"), "humidity"));

        JsonNode weatherArray = rootNode.get("weather");
        if (weatherArray != null && weatherArray.isArray() && weatherArray.size() > 0) {
            weatherDataMap.put("Description", getSafeText(weatherArray.get(0), "description").toUpperCase());
        } else {
            weatherDataMap.put("Description", "N/A");
        }

        return weatherDataMap;
    }

    private static String getSafeText(JsonNode node, String key) {
        return node != null && node.get(key) != null ? node.get(key).asText() : "N/A";
    }
}
