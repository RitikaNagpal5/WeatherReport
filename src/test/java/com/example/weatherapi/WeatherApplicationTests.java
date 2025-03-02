package com.example.weatherapi;// Test Cases

import com.example.weatherapi.exception.BadRequestException;
import com.example.weatherapi.model.Weather;
import com.example.weatherapi.repository.WeatherRepository;
import com.example.weatherapi.service.WeatherService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WeatherApiApplicationTests {

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private WeatherRepository weatherRepository;

    //invalid api key
    @Test
    void testInvalidApiKey() {
        assertThrows(BadRequestException.class,
                ()-> weatherService.getWeather("London", "UK", "INVALID_KEY"));

    }

    //rate limit test
    @Test
    void testRateLimitExceeded() {
        for (int i = 0; i < 5; i++) {
            weatherService.getWeather("London", "UK", "APIKEY2");
        }
        assertThrows(BadRequestException.class,
                ()-> weatherService.getWeather("London", "UK", "APIKEY2"));

    }

    //save data in h2
    @Test
    void testWeatherPersistence() {
        Weather weather = new Weather("Melbourne", "Australia", "Sunny");
        weatherRepository.save(weather);
        Optional<Weather> retrieved = weatherRepository.findByCityAndCountry("Melbourne", "Australia");
        assertTrue(retrieved.isPresent());
        assertEquals("Sunny", retrieved.get().getDescription());
    }
}