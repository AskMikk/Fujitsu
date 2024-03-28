package com.food.delivery.controller;

import com.food.delivery.service.DeliveryFeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DeliveryFeeController.class)
public class DeliveryFeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DeliveryFeeService deliveryFeeService;

    @Test
    public void calculateDeliveryFee_ShouldReturnTotalFee_WhenParametersAreValid() throws Exception {
        String expectedFee = "3 + 0 + 0 + 1 = 4";
        when(deliveryFeeService.calculateDeliveryFee(any(), any(), any()))
                .thenReturn(expectedFee);

        mockMvc.perform(get("/delivery-fee")
                        .param("city", "Tallinn")
                        .param("vehicleType", "Car"))
                .andExpect(status().isOk())
                .andExpect(content().string("Total delivery fee = RBF + ATEF + WSEF + WPEF = 3 + 0 + 0 + 1 = 4€"));
    }

    @Test
    public void calculateDeliveryFee_ShouldReturnBadRequest_WhenParametersAreInvalid() throws Exception {
        String errorMessage = "Invalid parameters";
        when(deliveryFeeService.calculateDeliveryFee(any(), any(), any()))
                .thenThrow(new IllegalArgumentException(errorMessage));

        mockMvc.perform(get("/delivery-fee")
                        .param("city", "UnknownCity")
                        .param("vehicleType", "UnknownVehicle"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(errorMessage));
    }
}
