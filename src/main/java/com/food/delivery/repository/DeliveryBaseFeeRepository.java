package com.food.delivery.repository;

import com.food.delivery.entity.City;
import com.food.delivery.entity.DeliveryBaseFee;
import com.food.delivery.entity.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface DeliveryBaseFeeRepository extends JpaRepository<DeliveryBaseFee, Long> {

    @Query("SELECT d.fee FROM DeliveryBaseFee d WHERE d.city = :city AND d.vehicleType = :vehicleType")
    Optional<BigDecimal> findFeeByCityAndVehicleType(City city, VehicleType vehicleType);
}
