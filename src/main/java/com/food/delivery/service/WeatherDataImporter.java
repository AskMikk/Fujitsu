package com.food.delivery.service;

import com.food.delivery.entity.WeatherPhenomenon;
import com.food.delivery.model.WeatherStation;
import com.food.delivery.model.WeatherStationsWrapper;
import com.food.delivery.entity.Weather;
import com.food.delivery.repository.WeatherPhenomenonRepository;
import com.food.delivery.repository.WeatherRepository;
import com.food.delivery.repository.CityRepository;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WeatherDataImporter {

    private final WeatherRepository weatherRepository;
    private final WeatherPhenomenonRepository weatherPhenomenonRepository;
    private final CityRepository cityRepository;
    private final Logger logger = LoggerFactory.getLogger(WeatherDataImporter.class);

    @Value("${weather.data.url}")
    private String weatherDataUrl;

    /**
     * Scheduled task to store weather data into the database.
     * This method fetches weather data from a predefined URL, filters relevant stations,
     * and stores the weather information into the database.
     * It runs at a scheduled time defined by the cron expression.
     */
    @Scheduled(cron = "0 15 * * * ?")
    public void storeWeatherData() {
        System.out.println("hello");
        List<WeatherStation> stations = fetchWeatherStations();
        System.out.println(stations);

        stations.stream()
                .filter(station -> Arrays.asList("Tallinn-Harku", "Tartu-Tõravere", "Pärnu").contains(station.getName()))
                .map(this::stationToWeather)
                .forEach(weatherRepository::save);
    }

    // TODO: mapper
    private Weather stationToWeather(WeatherStation station) {
        Weather weather = new Weather();
        String stationName = station.getName().split("-")[0];
        cityRepository.findByName(stationName).ifPresent(weather::setCity);

        weather.setWmoCode(station.getWmocode());
        weather.setAirTemperature(station.getAirtemperature());
        weather.setWindSpeed(station.getWindspeed());

        WeatherPhenomenon phenomenon = weatherPhenomenonRepository.findByName(station.getPhenomenon()).orElse(null);
        weather.setWeatherPhenomenon(phenomenon);
        weather.setTimestamp(LocalDateTime.now());
        return weather;
    }

    /**
     * Fetches weather stations from a predefined URL.
     * In case of an exception, a RuntimeException is thrown to be caught by GlobalExceptionHandler.
     * This ensures any fetching error is properly logged and handled.
     */
    List<WeatherStation> fetchWeatherStations() {
        try {
            URL url = new URL(weatherDataUrl);
            JAXBContext jaxbContext = JAXBContext.newInstance(WeatherStationsWrapper.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            WeatherStationsWrapper wrapper = (WeatherStationsWrapper) unmarshaller.unmarshal(url);
            return wrapper.getStations();
        } catch (Exception e) {
            logger.error("Error fetching weather data", e);
            throw new RuntimeException("Failed to fetch weather stations", e);
        }
    }
}
