package com.food.delivery.repository;

import com.food.delivery.entity.City;
import com.food.delivery.entity.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface WeatherRepository extends JpaRepository<Weather, Long> {
    Weather findTopByCityOrderByTimestampDesc(City stationName);

    Optional<Weather> findTopByCityAndTimestampBeforeOrderByTimestampDesc(City cityName, LocalDateTime dateTime);
}