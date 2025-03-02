// Service Layer
package com.example.weatherapi.service;

import com.example.weatherapi.dto.WeatherResponse;
import com.example.weatherapi.exception.BadRequestException;
import com.example.weatherapi.exception.InternalServerErrorException;
import com.example.weatherapi.model.Weather;
import com.example.weatherapi.repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.core.env.Environment;


@Service
public class WeatherService {
    private final WeatherRepository weatherRepository;
    private final RestTemplate restTemplate = new RestTemplate();
    private final Map<String, Integer> apiKeyUsage = new HashMap<>();
    private static final int API_LIMIT = 5;

    @Value("${weather.api.keys}")
    private List<String> validApiKeys;

    @Autowired
    private Environment env;

    private String getWeatherApiUrl(String city, String country) {
        String basePath = env.getProperty("openweather.api.basepath");
        String apiKey = env.getProperty("openweather.api.key");
        return String.format(basePath+"?q=%s,%s&appid=%s", city, country, apiKey);
    }
    public WeatherService(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    // Fetches weather data from OpenWeatherMap API or H2 database
    public ResponseEntity<WeatherResponse> getWeather(String city, String country, String apiKey) {
        if (!isValidApiKey(apiKey)) {
            throw new BadRequestException("Invalid API Key");
        }
        if (isRateLimited(apiKey)) {
            throw new BadRequestException("API rate limit exceeded");
        }

        Optional<Weather> cachedWeather = weatherRepository.findByCityAndCountry(city, country);
        if (cachedWeather.isPresent()) {
            return ResponseEntity.ok(new WeatherResponse(cachedWeather.get().getDescription()));
        }

        String url = getWeatherApiUrl(city, country);
        Map<String, Object> response = restTemplate.getForObject(url, Map.class);
        if (response == null || response.get("weather") == null) {
            throw new InternalServerErrorException("Failed to fetch weather data");
        }

        String description = ((Map<String, Object>) ((java.util.List<?>) response.get("weather")).get(0)).get("description").toString();
        Weather weather = new Weather(city, country, description);
        weatherRepository.save(weather);

        return ResponseEntity.ok(new WeatherResponse(description));
    }

    private boolean isValidApiKey(String apiKey) {
        return validApiKeys.contains(apiKey);
    }

    private boolean isRateLimited(String apiKey) {
        apiKeyUsage.put(apiKey, apiKeyUsage.getOrDefault(apiKey, 0) + 1);
        return apiKeyUsage.get(apiKey) > API_LIMIT;
    }
}