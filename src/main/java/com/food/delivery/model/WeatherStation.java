package com.food.delivery.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WeatherStation {
    @NotBlank(message = "Name cannot be blank")
    @Size(max = 255, message = "Name must not exceed 255 characters")
    private String name;

    @NotBlank(message = "WMO code is required.")
    private String wmocode;

    @NotNull(message = "Air temperature cannot be null")
    @DecimalMin(value = "-100.0", message = "Air temperature must be realistic")
    @DecimalMax(value = "60.0", message = "Air temperature must be realistic")
    private Double airtemperature;

    @NotNull(message = "Wind speed cannot be null")
    @DecimalMin(value = "0.0", message = "Wind speed cannot be negative")
    private Double windspeed;

    @NotBlank(message = "Phenomenon cannot be blank")
    private String phenomenon;
}
