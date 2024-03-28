package com.food.delivery.controller;

import com.food.delivery.service.DeliveryFeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "DeliveryFee", description = "API for delivery fee calculations.")
public class DeliveryFeeController {

    private final DeliveryFeeService deliveryFeeService;

    @GetMapping
    @Operation(summary = "Calculates the delivery fee based on city, vehicle type, and optionally date and time.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation. Returns the calculated delivery fee.",
                            content = @Content(mediaType = "text/plain")),
            })
    public String calculateDeliveryFee(
            @RequestParam String city,
            @RequestParam String vehicleType,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime dateTime) {
        return "Total delivery fee = RBF + ATEF + WSEF + WPEF = " + deliveryFeeService.calculateDeliveryFee(city, vehicleType, dateTime) + "€";
    }
}
