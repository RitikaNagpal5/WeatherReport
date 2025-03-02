// Controller Layer
package com.example.weatherapi.controller;

import com.example.weatherapi.dto.WeatherResponse;
import com.example.weatherapi.model.Weather;
import com.example.weatherapi.service.WeatherService;
import org.springframework.web.bind.annotation.*;
        import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {
    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    // Endpoint to get weather data based on city and country
    @GetMapping
    public ResponseEntity<WeatherResponse> getWeather(@RequestParam String city, @RequestParam String country, @RequestHeader("API-Key") String apiKey) {
        return weatherService.getWeather(city, country, apiKey);
    }
}
