package com.food.delivery.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "condition_types")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConditionType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Type cannot be blank")
    @Size(min = 2, max = 50, message = "Type must be between 2 and 50 characters")
    private String type;
}
