package com.food.delivery.controller;

import com.food.delivery.entity.ExtraFee;
import com.food.delivery.service.ExtraFeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/extra-fee")
@RequiredArgsConstructor
public class ExtraFeeController {

    private final ExtraFeeService extraFeeService;

    /**
     * API endpoint to create a new Extra Fee.
     * @param extraFee The Extra Fee entity to be created.
     * @return The created Extra Fee entity.
     */
    @PostMapping
    public ExtraFee createExtraFee(@Valid @RequestBody ExtraFee extraFee) {
        return extraFeeService.createExtraFee(extraFee);
    }

    /**
     * API endpoint to retrieve an Extra Fee by ID.
     * @param id The ID of the Extra Fee to retrieve.
     * @return The found Extra Fee entity.
     */
    @GetMapping("/{id}")
    public ExtraFee getExtraFee(@PathVariable Integer id) {
        return extraFeeService.getExtraFeeById(id);
    }

    /**
     * API endpoint to update an existing Extra Fee.
     * @param id The ID of the Extra Fee to update.
     * @param extraFee The new Extra Fee details.
     * @return The updated Extra Fee entity.
     */
    @PutMapping("/{id}")
    public ExtraFee updateExtraFee(@PathVariable Integer id, @RequestBody ExtraFee extraFee) {
        return extraFeeService.updateExtraFee(id, extraFee);
    }

    /**
     * API endpoint to delete an Extra Fee by ID.
     * @param id The ID of the Extra Fee to delete.
     */
    @DeleteMapping("/{id}")
    public void deleteExtraFee(@PathVariable Integer id) {
        extraFeeService.deleteExtraFee(id);
    }

    /**
     * API endpoint to list all Extra Fees.
     * @return The list of all Extra Fee entities.
     */
    @GetMapping
    public List<ExtraFee> listExtraFees() {
        return extraFeeService.listExtraFees();
    }

    /**
     * API endpoint to update only the fee amount of an Extra Fee.
     * @param id The ID of the Extra Fee to update.
     * @param feeUpdate Map containing the new fee amount.
     * @return The updated Extra Fee, or throw IllegalArgumentException if fee is not provided.
     */
    @PatchMapping("/{id}/update-fee")
    public ExtraFee updateExtraFeeAmount(@PathVariable Integer id, @RequestBody Map<String, BigDecimal> feeUpdate) {
        BigDecimal newFee = feeUpdate.get("fee");
        if (newFee == null) {
            throw new IllegalArgumentException("Fee amount must be provided.");
        }
        return extraFeeService.updateExtraFeeAmount(id, newFee);
    }
}
