package com.example.weatherapi.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Weather {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String city;
    private String country;
    private String description;

    public Weather() {}
    public Weather(String city, String country, String description) {
        this.city = city;
        this.country = country;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}