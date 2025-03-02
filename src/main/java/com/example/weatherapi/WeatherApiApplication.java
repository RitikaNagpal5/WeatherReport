package com.example.weatherapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.example.weatherapi")
public class WeatherApiApplication {
    private static final Logger logger = LoggerFactory.getLogger(WeatherApiApplication.class);

    public static void main(String[] args) {
        // Log application startup
        logger.info("Starting Weather API Application...");
        SpringApplication.run(WeatherApiApplication.class, args);
    }
}