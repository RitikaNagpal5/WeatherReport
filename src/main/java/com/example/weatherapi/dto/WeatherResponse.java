// DTO for API Response
package com.example.weatherapi.dto;

public class WeatherResponse {
    private String description;

    public WeatherResponse(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}