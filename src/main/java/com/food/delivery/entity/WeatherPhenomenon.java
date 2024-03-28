package com.food.delivery.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "weather_phenomena")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WeatherPhenomenon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Name cannot be blank.")
    @Size(max = 255, message = "Name must not exceed 255 characters.")
    private String name;
}
