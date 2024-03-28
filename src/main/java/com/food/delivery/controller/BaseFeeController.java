package com.food.delivery.controller;

import com.food.delivery.entity.DeliveryBaseFee;
import com.food.delivery.service.BaseFeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/base-fee")
@RequiredArgsConstructor
public class BaseFeeController {

    private final BaseFeeService deliveryBaseFeeService;

    /**
     * API endpoint to create a new delivery base fee.
     * @param deliveryBaseFee The delivery base fee to be created.
     * @return The created delivery base fee.
     */
    @PostMapping
    public DeliveryBaseFee createBaseFee(@Valid @RequestBody DeliveryBaseFee deliveryBaseFee) {
        return deliveryBaseFeeService.createBaseFee(deliveryBaseFee);
    }

    /**
     * API endpoint to retrieve a delivery base fee by ID.
     * @param id The ID of the delivery base fee to retrieve.
     * @return The found delivery base fee.
     */
    @GetMapping("/{id}")
    public DeliveryBaseFee getBaseFee(@PathVariable Integer id) {
        return deliveryBaseFeeService.getBaseFeeById(id);
    }

    /**
     * API endpoint to update an existing delivery base fee.
     * @param id The ID of the delivery base fee to update.
     * @param deliveryBaseFee The new delivery base fee details.
     * @return The updated delivery base fee.
     */
    @PutMapping("/{id}")
    public DeliveryBaseFee updateBaseFee(@PathVariable Integer id, @RequestBody DeliveryBaseFee deliveryBaseFee) {
        return deliveryBaseFeeService.updateBaseFee(id, deliveryBaseFee);
    }

    /**
     * API endpoint to delete a delivery base fee by ID.
     * @param id The ID of the delivery base fee to delete.
     */
    @DeleteMapping("/{id}")
    public void deleteBaseFee(@PathVariable Integer id) {
        deliveryBaseFeeService.deleteBaseFee(id);
    }

    /**
     * API endpoint to list all delivery base fees.
     * @return The list of all delivery base fees.
     */
    @GetMapping
    public List<DeliveryBaseFee> listBaseFees() {
        return deliveryBaseFeeService.listBaseFees();
    }

    /**
     * API endpoint to update only the fee amount of a delivery base fee.
     * @param id The ID of the delivery base fee to update.
     * @param feeUpdate Map containing the new fee amount.
     * @return The updated delivery base fee or null if fee is not provided.
     */
    @PatchMapping("/{id}/update-fee")
    public DeliveryBaseFee updateBaseFeeAmount(@PathVariable Integer id, @RequestBody Map<String, BigDecimal> feeUpdate) {
        BigDecimal newFee = feeUpdate.get("fee");
        if (newFee == null) {
            throw new IllegalArgumentException("Fee amount must be provided.");
        }
        return deliveryBaseFeeService.updateBaseFeeAmount(id, newFee);
    }
}
