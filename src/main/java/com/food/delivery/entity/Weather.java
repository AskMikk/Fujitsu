package com.food.delivery.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "weather")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Weather {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    @NotBlank(message = "WMO code is required.")
    private String wmoCode;

    @NotNull(message = "Air temperature cannot be null.")
    @DecimalMin(value = "-100.0", message = "Air temperature must be greater than -100 degrees.")
    @DecimalMax(value = "60.0", message = "Air temperature must be less than 60 degrees.")
    private Double airTemperature;

    @NotNull(message = "Wind speed cannot be null.")
    @DecimalMin(value = "0.0", message = "Wind speed cannot be negative.")
    private Double windSpeed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "phenomenon_id")
    private WeatherPhenomenon weatherPhenomenon;

    @NotNull(message = "Timestamp is required.")
    @PastOrPresent(message = "Timestamp must be in the past or present.")
    private LocalDateTime timestamp;
}
