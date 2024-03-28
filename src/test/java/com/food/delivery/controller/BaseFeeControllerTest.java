package com.food.delivery.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.food.delivery.entity.DeliveryBaseFee;
import com.food.delivery.service.BaseFeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BaseFeeController.class)
public class BaseFeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BaseFeeService baseFeeService;

    @Autowired
    private ObjectMapper objectMapper;

    private DeliveryBaseFee baseFee;

    @BeforeEach
    void setUp() {
        baseFee = new DeliveryBaseFee(1, null, null, BigDecimal.valueOf(10.00));
    }

    @Test
    void createBaseFee_Success() throws Exception {
        when(baseFeeService.createBaseFee(any(DeliveryBaseFee.class))).thenReturn(baseFee);

        mockMvc.perform(post("/base-fee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(baseFee)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(baseFee)));
    }

    @Test
    void updateBaseFeeAmount_Success() throws Exception {
        BigDecimal newFee = BigDecimal.valueOf(15.00);
        DeliveryBaseFee updatedFee = new DeliveryBaseFee(1, null, null, newFee);

        when(baseFeeService.updateBaseFeeAmount(any(Integer.class), any(BigDecimal.class))).thenReturn(updatedFee);

        mockMvc.perform(patch("/base-fee/1/update-fee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Collections.singletonMap("fee", newFee))))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(updatedFee)));
    }

    @Test
    void getBaseFee_Success() throws Exception {
        when(baseFeeService.getBaseFeeById(any(Integer.class))).thenReturn(baseFee);

        mockMvc.perform(get("/base-fee/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(baseFee)));
    }

    @Test
    void updateBaseFee_Success() throws Exception {
        DeliveryBaseFee updatedBaseFee = new DeliveryBaseFee(1, null, null, BigDecimal.valueOf(20.00));
        when(baseFeeService.updateBaseFee(any(Integer.class), any(DeliveryBaseFee.class))).thenReturn(updatedBaseFee);

        mockMvc.perform(put("/base-fee/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedBaseFee)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(updatedBaseFee)));
    }

    @Test
    void deleteBaseFee_Success() throws Exception {
        mockMvc.perform(delete("/base-fee/{id}", 1))
                .andExpect(status().isOk());

    }

    @Test
    void listBaseFees_Success() throws Exception {
        when(baseFeeService.listBaseFees()).thenReturn(Collections.singletonList(baseFee));

        mockMvc.perform(get("/base-fee")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Collections.singletonList(baseFee))));
    }
}
