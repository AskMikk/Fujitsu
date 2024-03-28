package com.food.delivery.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "delivery_base_fees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryBaseFee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false)
    @NotNull(message = "City is required")
    private City city;

    @ManyToOne
    @JoinColumn(name = "vehicle_type_id", nullable = false)
    @NotNull(message = "Vehicle type is required")
    private VehicleType vehicleType;

    @Column(nullable = false)
    @DecimalMin(value = "0.0", inclusive = false, message = "Fee must be greater than 0")
    private BigDecimal fee;
}
