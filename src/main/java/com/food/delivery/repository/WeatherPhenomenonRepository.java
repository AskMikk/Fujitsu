package com.food.delivery.repository;

import com.food.delivery.entity.WeatherPhenomenon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WeatherPhenomenonRepository extends JpaRepository<WeatherPhenomenon, Long> {
    Optional<WeatherPhenomenon> findByName(String phenomenon);
}