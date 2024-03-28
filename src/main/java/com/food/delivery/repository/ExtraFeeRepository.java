package com.food.delivery.repository;

import com.food.delivery.entity.ExtraFee;
import com.food.delivery.entity.VehicleType;
import com.food.delivery.entity.WeatherPhenomenon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Optional;

public interface ExtraFeeRepository extends JpaRepository<ExtraFee, Long> {

    @Query("SELECT ef.fee FROM ExtraFee ef WHERE ef.conditionType.id = 1 AND COALESCE(ef.conditionMin, -9999) <= :temperature AND ef.conditionMax >= :temperature AND ef.vehicleType = :vehicleType")
    Optional<BigDecimal> findTemperatureFeeForVehicleType(@Param("temperature") Double temperature,
                                                          @Param("vehicleType") VehicleType vehicleType);

    @Query("SELECT ef.fee FROM ExtraFee ef WHERE ef.conditionType.id = 2 AND ef.conditionMin <= :windSpeed AND ef.conditionMax >= :windSpeed AND ef.vehicleType = :vehicleType")
    Optional<BigDecimal> findWindSpeedFeeForVehicleType(@Param("windSpeed") Double windSpeed,
                                                        @Param("vehicleType") VehicleType vehicleType);

    @Query("SELECT ef.fee FROM ExtraFee ef WHERE ef.phenomenon = :phenomenon AND ef.vehicleType = :vehicleType")
    Optional<BigDecimal> findFeeForWeatherPhenomenonAndVehicleType(@Param("phenomenon") WeatherPhenomenon phenomenon,
                                                                   @Param("vehicleType") VehicleType vehicleType);

    @Query("SELECT ef FROM ExtraFee ef WHERE ef.isError = TRUE AND " +
            "((ef.conditionType.id = 3 AND ef.phenomenon.id = :phenomenonId) OR " +
            "(ef.conditionType.id = 2 AND :windSpeed > 20.0)) AND " +
            "ef.vehicleType.type = :vehicleType")
    Optional<ExtraFee[]> findErrorCondition(@Param("windSpeed") Double windSpeed,
                                            @Param("phenomenonId") Long phenomenonId,
                                            @Param("vehicleType") String vehicleType);
}
