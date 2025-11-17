package com.renault.garage.services.impl;


import com.renault.garage.dtos.AccessoryDTO;
import com.renault.garage.entities.Accessory;
import com.renault.garage.entities.Vehicle;
import com.renault.garage.exceptions.BusinessException;
import com.renault.garage.mappers.AccessoryMapper;
import com.renault.garage.repositories.AccessoryRepository;
import com.renault.garage.repositories.VehicleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccessoryServiceImplTest {

    @Mock
    private AccessoryRepository accessoryRepository;
    @Mock
    private VehicleRepository vehicleRepository;
    @Mock
    private AccessoryMapper accessoryMapper;

    @InjectMocks
    private AccessoryServiceImpl accessoryService;

    private AccessoryDTO accessoryDTO;
    private Accessory accessory;
    private Vehicle vehicle;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        accessoryDTO = new AccessoryDTO(
                null,
                "GPS",
                "Navigation system",
                BigDecimal.valueOf(250.0),
                "Electronics"
        );

        accessory = new Accessory();
        accessory.setId(1L);

        vehicle = new Vehicle();
        vehicle.setId(10L);
    }

    @Test
    void testAddAccessoryToVehicle_success() {
        when(vehicleRepository.findById(10L)).thenReturn(Optional.of(vehicle));
        when(accessoryMapper.toEntity(accessoryDTO)).thenReturn(accessory);
        when(accessoryRepository.save(accessory)).thenReturn(accessory);
        when(accessoryMapper.toDto(accessory)).thenReturn(accessoryDTO);

        AccessoryDTO result = accessoryService.addAccessoryToVehicle(10L, accessoryDTO);

        assertNotNull(result);
        verify(accessoryRepository).save(accessory);
        assertEquals(vehicle, accessory.getVehicle());
        assertEquals("GPS", result.name());
    }

    @Test
    void testAddAccessoryToVehicle_vehicleNotFound() {
        when(vehicleRepository.findById(10L)).thenReturn(Optional.empty());

        BusinessException ex = assertThrows(BusinessException.class,
                () -> accessoryService.addAccessoryToVehicle(10L, accessoryDTO));

        assertEquals("Vehicle not found", ex.getMessage());
    }


    @Test
    void testUpdateAccessory_success() {
        when(accessoryRepository.findById(1L)).thenReturn(Optional.of(accessory));
        when(accessoryRepository.save(accessory)).thenReturn(accessory);
        when(accessoryMapper.toDto(accessory)).thenReturn(accessoryDTO);

        AccessoryDTO result = accessoryService.updateAccessory(1L, accessoryDTO);

        assertNotNull(result);
        assertEquals("GPS", accessory.getName());
        verify(accessoryRepository).save(accessory);
    }

    @Test
    void testUpdateAccessory_accessoryNotFound() {
        when(accessoryRepository.findById(1L)).thenReturn(Optional.empty());

        BusinessException ex = assertThrows(BusinessException.class,
                () -> accessoryService.updateAccessory(1L, accessoryDTO));

        assertEquals("Accessory not found", ex.getMessage());
    }


    @Test
    void testDeleteAccessory() {
        accessoryService.deleteAccessory(1L);
        verify(accessoryRepository).deleteById(1L);
    }
}

