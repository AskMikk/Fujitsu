package com.food.delivery.service;

import com.food.delivery.entity.City;
import com.food.delivery.entity.WeatherPhenomenon;
import com.food.delivery.model.WeatherStation;
import com.food.delivery.repository.CityRepository;
import com.food.delivery.repository.WeatherPhenomenonRepository;
import com.food.delivery.repository.WeatherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class WeatherDataImporterTest {

    @Mock
    private WeatherRepository weatherRepository;
    @Mock
    private WeatherPhenomenonRepository weatherPhenomenonRepository;
    @Mock
    private CityRepository cityRepository;
    @Spy
    @InjectMocks
    private WeatherDataImporter weatherDataImporter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(weatherDataImporter, "weatherDataUrl", "https://www.ilmateenistus.ee/ilma_andmed/xml/observations.php");
    }

    @Test
    public void testStoreWeatherData_savesRelevantWeatherData() {
        WeatherStation station1 = new WeatherStation("Tallinn-Harku", "12345", 5.0, 10.0, "Clear");
        WeatherStation station2 = new WeatherStation("Pärnu", "67890", 3.0, 5.0, "Cloudy");

        doReturn(Arrays.asList(station1, station2)).when(weatherDataImporter).fetchWeatherStations();
        when(cityRepository.findByName(anyString())).thenReturn(Optional.of(new City()));
        when(weatherPhenomenonRepository.findByName(anyString())).thenReturn(Optional.of(new WeatherPhenomenon()));

        weatherDataImporter.storeWeatherData();

        verify(weatherRepository, times(2)).save(any());
    }
}
