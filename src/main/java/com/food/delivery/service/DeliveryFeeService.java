package com.food.delivery.service;

import com.food.delivery.entity.City;
import com.food.delivery.entity.VehicleType;
import com.food.delivery.entity.Weather;
import com.food.delivery.exception.BaseFeeNotFoundException;
import com.food.delivery.exception.CityNotFoundException;
import com.food.delivery.exception.VehicleTypeNotFoundException;
import com.food.delivery.exception.WeatherConditionException;
import com.food.delivery.repository.CityRepository;
import com.food.delivery.repository.DeliveryBaseFeeRepository;
import com.food.delivery.repository.ExtraFeeRepository;
import com.food.delivery.repository.VehicleTypeRepository;
import com.food.delivery.repository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DeliveryFeeService {

    private final WeatherRepository weatherRepository;
    private final DeliveryBaseFeeRepository deliveryBaseFeeRepository;
    private final ExtraFeeRepository extraFeeRepository;
    private final CityRepository cityRepository;
    private final VehicleTypeRepository vehicleTypeRepository;

    /**
     * Calculates the total delivery fee based on city, vehicle type, and date/time,
     * considering weather conditions.
     * @param cityName The name of the city where the delivery is to be made.
     * @param vehicleTypeName The type of vehicle used for delivery.
     * @param dateTime The date and time when the delivery is scheduled.
     * @return A string representation of the total fee calculation.
     * @throws IllegalArgumentException If the city, vehicle type, or weather data is not found.
     */
    public String calculateDeliveryFee(String cityName, String vehicleTypeName, LocalDateTime dateTime) {
        Weather weather;
        City city = cityRepository.findByName(cityName)
                .orElseThrow(() -> new CityNotFoundException("City not found"));
        VehicleType vehicleType = vehicleTypeRepository.findByType(vehicleTypeName)
                .orElseThrow(() -> new VehicleTypeNotFoundException("Vehicle type not found"));
        if (dateTime != null) {
            weather = weatherRepository.findTopByCityAndTimestampBeforeOrderByTimestampDesc(city, dateTime)
                    .orElseThrow(() -> new IllegalArgumentException("Weather data not found for specified time"));
        } else {
            weather = weatherRepository.findTopByCityOrderByTimestampDesc(city);
        }

        BigDecimal baseFee = calculateBaseFee(city, vehicleType);
        checkForErrorConditions(weather, vehicleTypeName);

        BigDecimal extraTemperatureFee = calculateExtraTemperatureFee(weather, vehicleType);
        BigDecimal extraWindFee = calculateExtraWindFee(weather, vehicleType);
        BigDecimal extraPhenomenonFee = calculateExtraPhenomenonFee(weather, vehicleType);

        BigDecimal extraFees = extraTemperatureFee.add(extraWindFee).add(extraPhenomenonFee);

        BigDecimal totalFee = baseFee.add(extraFees);

        return String.format("%s + %s + %s + %s = %s",
                baseFee.toPlainString(),
                extraTemperatureFee.toPlainString(),
                extraWindFee.toPlainString(),
                extraPhenomenonFee.toPlainString(),
                totalFee.toPlainString());
    }


    private BigDecimal calculateBaseFee(City city, VehicleType vehicleType) {
        return deliveryBaseFeeRepository.findFeeByCityAndVehicleType(city, vehicleType)
                .orElseThrow(() -> new BaseFeeNotFoundException("Base fee not found for city and vehicle type"));
    }

    private BigDecimal calculateExtraTemperatureFee(Weather weather, VehicleType vehicleType) {
        System.out.println(weather);
        return extraFeeRepository.findTemperatureFeeForVehicleType(weather.getAirTemperature(), vehicleType)
                .orElse(BigDecimal.ZERO);
    }

    private BigDecimal calculateExtraWindFee(Weather weather, VehicleType vehicleType) {
        return extraFeeRepository.findWindSpeedFeeForVehicleType(weather.getWindSpeed(), vehicleType)
                .orElse(BigDecimal.ZERO);
    }

    private BigDecimal calculateExtraPhenomenonFee(Weather weather, VehicleType vehicleType) {
        return extraFeeRepository.findFeeForWeatherPhenomenonAndVehicleType(weather.getWeatherPhenomenon(), vehicleType)
                .orElse(BigDecimal.ZERO);
    }

    private void checkForErrorConditions(Weather weather, String vehicleType) {
        extraFeeRepository.findErrorCondition(
                        weather.getWindSpeed(),
                        weather.getWeatherPhenomenon() != null ? weather.getWeatherPhenomenon().getId() : null,
                        vehicleType)
                .ifPresent(errorConditions -> {
                    if (errorConditions.length > 0) {
                        throw new WeatherConditionException("Usage of selected vehicle type is forbidden due to current weather conditions");
                    }
                });
    }
}
