package com.food.delivery.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.food.delivery.entity.ExtraFee;
import com.food.delivery.service.ExtraFeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ExtraFeeController.class)
public class ExtraFeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExtraFeeService extraFeeService;

    @Autowired
    private ObjectMapper objectMapper;

    private ExtraFee extraFee;

    @BeforeEach
    void setUp() {
        extraFee = new ExtraFee();
    }

    @Test
    void createExtraFee_Success() throws Exception {
        when(extraFeeService.createExtraFee(any(ExtraFee.class))).thenReturn(extraFee);

        mockMvc.perform(post("/extra-fee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(extraFee)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(extraFee)));
    }

    @Test
    void getExtraFee_Success() throws Exception {
        when(extraFeeService.getExtraFeeById(any(Integer.class))).thenReturn(extraFee);

        mockMvc.perform(get("/extra-fee/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(extraFee)));
    }

    @Test
    void updateExtraFee_Success() throws Exception {
        when(extraFeeService.updateExtraFee(any(Integer.class), any(ExtraFee.class))).thenReturn(extraFee);

        mockMvc.perform(put("/extra-fee/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(extraFee)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(extraFee)));
    }

    @Test
    void deleteExtraFee_Success() throws Exception {
        mockMvc.perform(delete("/extra-fee/{id}", 1))
                .andExpect(status().isOk());
    }

    @Test
    void listExtraFees_Success() throws Exception {
        when(extraFeeService.listExtraFees()).thenReturn(List.of(extraFee));

        mockMvc.perform(get("/extra-fee")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(extraFee))));
    }

    @Test
    void updateExtraFeeAmount_Success() throws Exception {
        BigDecimal newFee = BigDecimal.valueOf(20.00);
        ExtraFee updatedExtraFee = new ExtraFee();
        when(extraFeeService.updateExtraFeeAmount(any(Integer.class), any(BigDecimal.class))).thenReturn(updatedExtraFee);

        mockMvc.perform(patch("/extra-fee/{id}/update-fee", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Collections.singletonMap("fee", newFee))))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(updatedExtraFee)));
    }
}
