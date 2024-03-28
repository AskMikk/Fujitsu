package com.food.delivery.controller;

import com.food.delivery.entity.DeliveryBaseFee;
import com.food.delivery.model.FeeUpdateRequest;
import com.food.delivery.service.BaseFeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/base-fee")
@RequiredArgsConstructor
@Tag(name = "BaseFee", description = "API for base fee operations.")
public class BaseFeeController {

    private final BaseFeeService baseFeeService;

    @PostMapping
    @Operation(summary = "Create New Delivery Base Fee",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Delivery Base Fee successfully created.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = DeliveryBaseFee.class)))
            })
    public DeliveryBaseFee createBaseFee(@Valid @RequestBody DeliveryBaseFee deliveryBaseFee) {
        return baseFeeService.createBaseFee(deliveryBaseFee);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retrieve Delivery Base Fee by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Delivery Base Fee found.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = DeliveryBaseFee.class)))
            })
    public DeliveryBaseFee getBaseFee(@PathVariable Integer id) {
        return baseFeeService.getBaseFeeById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Existing Delivery Base Fee",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Delivery Base Fee successfully updated.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = DeliveryBaseFee.class)))
            })
    public DeliveryBaseFee updateBaseFee(@PathVariable Integer id, @RequestBody DeliveryBaseFee deliveryBaseFee) {
        return baseFeeService.updateBaseFee(id, deliveryBaseFee);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Delivery Base Fee by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Delivery Base Fee successfully deleted.")
            })
    public void deleteBaseFee(@PathVariable Integer id) {
        baseFeeService.deleteBaseFee(id);
    }

    @GetMapping
    @Operation(summary = "List All Delivery Base Fees",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of all Delivery Base Fees.",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = DeliveryBaseFee.class))))
            })
    public List<DeliveryBaseFee> listBaseFees() {
        return baseFeeService.listBaseFees();
    }

    @PatchMapping("/{id}/update-fee")
    @Operation(summary = "Updates only the fee amount of a Delivery Base Fee entity by its ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Delivery Base Fee amount successfully updated.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = DeliveryBaseFee.class)))
            })
    public DeliveryBaseFee updateBaseFeeAmount(@PathVariable Integer id, @Valid @RequestBody FeeUpdateRequest feeUpdateRequest) {
        BigDecimal newFee = feeUpdateRequest.getFee();
        if (newFee == null) {
            throw new IllegalArgumentException("Fee amount must be provided.");
        }
        return baseFeeService.updateBaseFeeAmount(id, newFee);
    }
}
