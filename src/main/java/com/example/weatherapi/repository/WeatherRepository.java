// Repository Layer
package com.example.weatherapi.repository;

import com.example.weatherapi.model.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface WeatherRepository extends JpaRepository<Weather, Long> {
    Optional<Weather> findByCityAndCountry(String city, String country);
}
