package com.food.delivery.service;

import com.food.delivery.entity.City;
import com.food.delivery.entity.DeliveryBaseFee;
import com.food.delivery.entity.VehicleType;
import com.food.delivery.repository.DeliveryBaseFeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class BaseFeeServiceTest {

    @Mock
    private DeliveryBaseFeeRepository deliveryBaseFeeRepository;

    @InjectMocks
    private BaseFeeService baseFeeService;

    private DeliveryBaseFee baseFee;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        City city = new City(1, "CityName");
        VehicleType vehicleType = new VehicleType(1, "VehicleType");
        baseFee = new DeliveryBaseFee(1, city, vehicleType, new BigDecimal("5.00"));
    }

    @Test
    public void createBaseFee_Success() {
        when(deliveryBaseFeeRepository.save(any(DeliveryBaseFee.class))).thenReturn(baseFee);
        DeliveryBaseFee created = baseFeeService.createBaseFee(baseFee);
        assertEquals(baseFee, created);
    }

    @Test
    public void getBaseFeeById_Success() {
        when(deliveryBaseFeeRepository.findById(1L)).thenReturn(Optional.of(baseFee));
        DeliveryBaseFee found = baseFeeService.getBaseFeeById(1);
        assertEquals(baseFee, found);
    }

    @Test
    public void getBaseFeeById_NotFound() {
        when(deliveryBaseFeeRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> baseFeeService.getBaseFeeById(1));
    }

    @Test
    public void updateBaseFee_Success() {
        when(deliveryBaseFeeRepository.findById(1L)).thenReturn(Optional.of(baseFee));
        when(deliveryBaseFeeRepository.save(any(DeliveryBaseFee.class))).thenReturn(baseFee);
        DeliveryBaseFee updated = baseFeeService.updateBaseFee(1, baseFee);
        assertEquals(baseFee, updated);
    }

    @Test
    public void deleteBaseFee_Success() {
        doNothing().when(deliveryBaseFeeRepository).deleteById(1L);
        baseFeeService.deleteBaseFee(1);
        verify(deliveryBaseFeeRepository, times(1)).deleteById(1L);
    }

    @Test
    public void listBaseFees_Success() {
        when(deliveryBaseFeeRepository.findAll()).thenReturn(Collections.singletonList(baseFee));
        List<DeliveryBaseFee> fees = baseFeeService.listBaseFees();
        assertEquals(1, fees.size());
        assertEquals(baseFee, fees.get(0));
    }

    @Test
    public void updateBaseFeeAmount_Success() {
        when(deliveryBaseFeeRepository.findById(1L)).thenReturn(Optional.of(baseFee));
        when(deliveryBaseFeeRepository.save(any(DeliveryBaseFee.class))).thenReturn(baseFee);
        DeliveryBaseFee updated = baseFeeService.updateBaseFeeAmount(1, new BigDecimal("10.00"));
        assertEquals(new BigDecimal("10.00"), updated.getFee());
    }
}
