package com.food.delivery.controller;

import com.food.delivery.service.DeliveryFeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/delivery-fee")
@RequiredArgsConstructor
public class DeliveryFeeController {

    private final DeliveryFeeService deliveryFeeService;

    /**
     * API endpoint to calculate the delivery fee based on city, vehicle type, and optionally date and time.
     * @param city The city for delivery.
     * @param vehicleType The type of vehicle used for delivery.
     * @param dateTime The date and time for delivery (optional).
     * @return A string with either the calculated delivery fee or an error message.
     * In case of error, IllegalArgumentException will handle it.
     */
    @GetMapping
    public String calculateDeliveryFee(
            @RequestParam String city,
            @RequestParam String vehicleType,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime dateTime) {
        return "Total delivery fee = RBF + ATEF + WSEF + WPEF = " + deliveryFeeService.calculateDeliveryFee(city, vehicleType, dateTime) + "€";
    }
}
