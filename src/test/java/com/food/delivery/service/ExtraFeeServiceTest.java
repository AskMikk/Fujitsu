package com.food.delivery.service;

import com.food.delivery.entity.ExtraFee;
import com.food.delivery.repository.ExtraFeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ExtraFeeServiceTest {

    @Mock
    private ExtraFeeRepository extraFeeRepository;

    @InjectMocks
    private ExtraFeeService extraFeeService;

    private ExtraFee extraFee;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        extraFee = new ExtraFee();
        extraFee.setId(1);
        extraFee.setFee(new BigDecimal("10.00"));
    }

    @Test
    void createExtraFeeTest() {
        when(extraFeeRepository.save(any(ExtraFee.class))).thenReturn(extraFee);
        ExtraFee created = extraFeeService.createExtraFee(extraFee);
        assertEquals(extraFee.getId(), created.getId());
        verify(extraFeeRepository, times(1)).save(any(ExtraFee.class));
    }

    @Test
    void getExtraFeeByIdTest() {
        when(extraFeeRepository.findById(anyLong())).thenReturn(Optional.of(extraFee));
        ExtraFee found = extraFeeService.getExtraFeeById(extraFee.getId());
        assertEquals(extraFee.getId(), found.getId());
    }

    @Test
    void updateExtraFeeTest() {
        when(extraFeeRepository.findById(anyLong())).thenReturn(Optional.of(extraFee));
        when(extraFeeRepository.save(any(ExtraFee.class))).thenReturn(extraFee);
        extraFee.setFee(new BigDecimal("15.00"));
        ExtraFee updated = extraFeeService.updateExtraFee(extraFee.getId(), extraFee);
        assertEquals(new BigDecimal("15.00"), updated.getFee());
    }

    @Test
    void deleteExtraFeeTest() {
        extraFeeService.deleteExtraFee(extraFee.getId());
        verify(extraFeeRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void listExtraFeesTest() {
        when(extraFeeRepository.findAll()).thenReturn(List.of(extraFee));
        List<ExtraFee> fees = extraFeeService.listExtraFees();
        assertEquals(1, fees.size());
        assertEquals(extraFee.getId(), fees.get(0).getId());
    }

    @Test
    void updateExtraFeeAmountTest() {
        when(extraFeeRepository.findById(anyLong())).thenReturn(Optional.of(extraFee));
        when(extraFeeRepository.save(any(ExtraFee.class))).thenReturn(extraFee);
        extraFee.setFee(new BigDecimal("20.00"));
        ExtraFee updated = extraFeeService.updateExtraFeeAmount(extraFee.getId(), new BigDecimal("20.00"));
        assertEquals(new BigDecimal("20.00"), updated.getFee());
        verify(extraFeeRepository, times(1)).save(extraFee);
    }
}
