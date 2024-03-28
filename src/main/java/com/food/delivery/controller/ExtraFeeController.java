package com.food.delivery.controller;

import com.food.delivery.entity.ExtraFee;
import com.food.delivery.model.FeeUpdateRequest;
import com.food.delivery.service.ExtraFeeService;
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
@RequestMapping("/extra-fee")
@RequiredArgsConstructor
@Tag(name = "ExtraFee", description = "API for managing extra fees.")
public class ExtraFeeController {

    private final ExtraFeeService extraFeeService;

    @PostMapping
    @Operation(summary = "Create New Extra Fee",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Extra Fee successfully created.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExtraFee.class)))
            })
    public ExtraFee createExtraFee(@Valid @RequestBody ExtraFee extraFee) {
        return extraFeeService.createExtraFee(extraFee);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retrieve Extra Fee by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Extra Fee found.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExtraFee.class)))
            })
    public ExtraFee getExtraFee(@PathVariable Integer id) {
        return extraFeeService.getExtraFeeById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Existing Extra Fee",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Extra Fee successfully updated.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExtraFee.class)))
            })
    public ExtraFee updateExtraFee(@PathVariable Integer id, @RequestBody ExtraFee extraFee) {
        return extraFeeService.updateExtraFee(id, extraFee);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Extra Fee by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Extra Fee successfully deleted.")
            })
    public void deleteExtraFee(@PathVariable Integer id) {
        extraFeeService.deleteExtraFee(id);
    }

    @GetMapping
    @Operation(summary = "List All Extra Fees",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of all Extra Fees.",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = ExtraFee.class))))
            })
    public List<ExtraFee> listExtraFees() {
        return extraFeeService.listExtraFees();
    }

    @PatchMapping("/{id}/update-fee")
    @Operation(summary = "Updates only the fee amount of an Extra Fee entity by its ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Extra Fee amount successfully updated.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExtraFee.class)))
            })
    public ExtraFee updateExtraFeeAmount(@PathVariable Integer id, @Valid @RequestBody FeeUpdateRequest feeUpdateRequest) {
        BigDecimal newFee = feeUpdateRequest.getFee();
        if (newFee == null) {
            throw new IllegalArgumentException("Fee amount must be provided.");
        }
        return extraFeeService.updateExtraFeeAmount(id, newFee);
    }
}
