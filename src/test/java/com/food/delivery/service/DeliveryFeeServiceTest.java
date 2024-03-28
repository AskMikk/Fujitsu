package com.food.delivery.service;

import com.food.delivery.entity.City;
import com.food.delivery.entity.VehicleType;
import com.food.delivery.entity.Weather;
import com.food.delivery.exception.CityNotFoundException;
import com.food.delivery.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class DeliveryFeeServiceTest {

    @Mock
    private WeatherRepository weatherRepository;
    @Mock
    private DeliveryBaseFeeRepository deliveryBaseFeeRepository;
    @Mock
    private ExtraFeeRepository extraFeeRepository;
    @Mock
    private CityRepository cityRepository;
    @Mock
    private VehicleTypeRepository vehicleTypeRepository;

    @InjectMocks
    private DeliveryFeeService deliveryFeeService;

    private final LocalDateTime dateTime = LocalDateTime.of(2024, 3, 28, 10, 0);

    @BeforeEach
    void setup() {
        City city = new City();
        city.setName("TestCity");

        VehicleType vehicleType = new VehicleType();
        vehicleType.setType("Car");

        Weather weather = new Weather();
        weather.setAirTemperature(20.0);
        weather.setWindSpeed(5.0);
        weather.setWeatherPhenomenon(null);

        when(cityRepository.findByName("TestCity")).thenReturn(Optional.of(city));
        when(vehicleTypeRepository.findByType("Car")).thenReturn(Optional.of(vehicleType));
        when(weatherRepository.findTopByCityAndTimestampBeforeOrderByTimestampDesc(any(City.class), any(LocalDateTime.class)))
                .thenReturn(Optional.of(weather));
        when(deliveryBaseFeeRepository.findFeeByCityAndVehicleType(any(City.class), any(VehicleType.class)))
                .thenReturn(Optional.of(BigDecimal.valueOf(4)));
        when(extraFeeRepository.findTemperatureFeeForVehicleType(any(Double.class), any(VehicleType.class)))
                .thenReturn(Optional.of(BigDecimal.valueOf(0)));
        when(extraFeeRepository.findWindSpeedFeeForVehicleType(any(Double.class), any(VehicleType.class)))
                .thenReturn(Optional.of(BigDecimal.valueOf(0)));
        when(extraFeeRepository.findFeeForWeatherPhenomenonAndVehicleType(any(), any(VehicleType.class)))
                .thenReturn(Optional.of(BigDecimal.valueOf(0)));
    }

    @Test
    void calculateDeliveryFee_ShouldReturnCorrectFee_WhenParametersAreValid() {
        String result = deliveryFeeService.calculateDeliveryFee("TestCity", "Car", dateTime);

        assertNotNull(result);
        assertTrue(result.contains("="));
    }

    @Test
    void calculateDeliveryFee_ShouldThrowIllegalArgumentException_WhenCityNotFound() {
        when(cityRepository.findByName("UnknownCity")).thenReturn(Optional.empty());

        assertThrows(CityNotFoundException.class, () ->
                        deliveryFeeService.calculateDeliveryFee("UnknownCity", "Car", dateTime),
                "City not found"
        );
    }
}
