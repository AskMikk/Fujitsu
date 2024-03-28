package com.food.delivery.service;

import com.food.delivery.entity.ExtraFee;
import com.food.delivery.exception.ExtraFeeNotFoundException;
import com.food.delivery.repository.ExtraFeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExtraFeeService {

    private final ExtraFeeRepository extraFeeRepository;

    /**
     * Creates and saves a new ExtraFee entity.
     * @param extraFee The ExtraFee entity to be saved.
     * @return The saved ExtraFee entity.
     */
    public ExtraFee createExtraFee(ExtraFee extraFee) {
        return extraFeeRepository.save(extraFee);
    }

    /**
     * Retrieves an ExtraFee entity by its ID.
     * @param id The ID of the ExtraFee entity to retrieve.
     * @return The retrieved ExtraFee entity.
     * @throws RuntimeException If no ExtraFee entity with the given ID is found.
     */
    public ExtraFee getExtraFeeById(Integer id) {
        return extraFeeRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new ExtraFeeNotFoundException("Extra fee not found for id: " + id));
    }

    /**
     * Updates an existing ExtraFee entity with new details.
     * @param id The ID of the ExtraFee entity to update.
     * @param extraFeeDetails A ExtraFee entity containing the details to update.
     * @return The updated ExtraFee entity.
     */
    public ExtraFee updateExtraFee(Integer id, ExtraFee extraFeeDetails) {
        ExtraFee extraFee = getExtraFeeById(id);
        extraFee.setConditionType(extraFeeDetails.getConditionType());
        extraFee.setConditionMin(extraFeeDetails.getConditionMin());
        extraFee.setConditionMax(extraFeeDetails.getConditionMax());
        extraFee.setVehicleType(extraFeeDetails.getVehicleType());
        extraFee.setFee(extraFeeDetails.getFee());
        extraFee.setPhenomenon(extraFeeDetails.getPhenomenon());
        extraFee.setIsError(extraFeeDetails.getIsError());
        return extraFeeRepository.save(extraFee);
    }

    /**
     * Deletes an ExtraFee entity by its ID.
     * @param id The ID of the ExtraFee entity to delete.
     */
    public void deleteExtraFee(Integer id) {
        extraFeeRepository.deleteById(Long.valueOf(id));
    }

    /**
     * Retrieves a list of all ExtraFee entities.
     * @return A list of all ExtraFee entities.
     */
    public List<ExtraFee> listExtraFees() {
        return extraFeeRepository.findAll();
    }

    /**
     * Updates the fee amount of an existing ExtraFee entity.
     * @param id The ID of the ExtraFee entity to update.
     * @param newFee The new fee amount to set.
     * @return The updated ExtraFee entity.
     * @throws RuntimeException If no ExtraFee entity with the given ID is found.
     */
    public ExtraFee updateExtraFeeAmount(Integer id, BigDecimal newFee) {
        ExtraFee extraFee = extraFeeRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new ExtraFeeNotFoundException("Extra fee not found for id: " + id));
        extraFee.setFee(newFee);
        if (Boolean.TRUE.equals(extraFee.getIsError())) {
            extraFee.setIsError(false);
        }
        return extraFeeRepository.save(extraFee);
    }
}
