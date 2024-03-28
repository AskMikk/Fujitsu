package com.food.delivery.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "extra_fees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExtraFee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "condition_type_id", nullable = false)
    @NotNull(message = "Condition type is required")
    private ConditionType conditionType;

    private Double conditionMin;

    private Double conditionMax;

    @ManyToOne
    @JoinColumn(name = "vehicle_type_id", nullable = false)
    @NotNull(message = "Vehicle type is required")
    private VehicleType vehicleType;

    private BigDecimal fee;

    @ManyToOne
    @JoinColumn(name = "phenomenon_id")
    private WeatherPhenomenon phenomenon;

    @Column(name = "error")
    private Boolean isError;
}
