package com.food.delivery.service;

import com.food.delivery.entity.DeliveryBaseFee;
import com.food.delivery.exception.BaseFeeNotFoundException;
import com.food.delivery.repository.DeliveryBaseFeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BaseFeeService {

    private final DeliveryBaseFeeRepository deliveryBaseFeeRepository;

    /**
     * Creates and saves a new delivery base fee.
     * @param deliveryBaseFee The delivery base fee to save.
     * @return The saved delivery base fee.
     */
    public DeliveryBaseFee createBaseFee(DeliveryBaseFee deliveryBaseFee) {
        return deliveryBaseFeeRepository.save(deliveryBaseFee);
    }

    /**
     * Retrieves a delivery base fee by its ID.
     * @param id The ID of the delivery base fee.
     * @return The found delivery base fee.
     * @throws RuntimeException If the delivery base fee is not found.
     */
    public DeliveryBaseFee getBaseFeeById(Integer id) {
        return deliveryBaseFeeRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new BaseFeeNotFoundException("Base fee not found for id: " + id));
    }


    /**
     * Updates the details of an existing delivery base fee.
     * @param id The ID of the delivery base fee to update.
     * @param deliveryBaseFeeDetails The new details to set.
     * @return The updated delivery base fee.
     */
    public DeliveryBaseFee updateBaseFee(Integer id, DeliveryBaseFee deliveryBaseFeeDetails) {
        DeliveryBaseFee deliveryBaseFee = getBaseFeeById(id);
        deliveryBaseFee.setCity(deliveryBaseFeeDetails.getCity());
        deliveryBaseFee.setVehicleType(deliveryBaseFeeDetails.getVehicleType());
        deliveryBaseFee.setFee(deliveryBaseFeeDetails.getFee());
        return deliveryBaseFeeRepository.save(deliveryBaseFee);
    }

    /**
     * Deletes a delivery base fee by its ID.
     * @param id The ID of the delivery base fee to delete.
     */
    public void deleteBaseFee(Integer id) {
        deliveryBaseFeeRepository.deleteById(Long.valueOf(id));
    }

    /**
     * Lists all delivery base fee entries.
     * @return A list of all delivery base fees.
     */
    public List<DeliveryBaseFee> listBaseFees() {
        return deliveryBaseFeeRepository.findAll();
    }

    /**
     * Updates only the fee amount of an existing delivery base fee.
     * @param id The ID of the delivery base fee to update.
     * @param newFee The new fee amount.
     * @return The updated delivery base fee.
     * @throws RuntimeException If the delivery base fee is not found.
     */
    public DeliveryBaseFee updateBaseFeeAmount(Integer id, BigDecimal newFee) {
        DeliveryBaseFee deliveryBaseFee = deliveryBaseFeeRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new BaseFeeNotFoundException("Base fee not found for id: " + id));
        deliveryBaseFee.setFee(newFee);
        return deliveryBaseFeeRepository.save(deliveryBaseFee);
    }
}