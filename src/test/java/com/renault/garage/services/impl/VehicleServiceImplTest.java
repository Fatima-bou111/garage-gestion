package com.renault.garage.services.impl;


import com.renault.garage.dtos.VehicleDTO;
import com.renault.garage.entities.Garage;
import com.renault.garage.entities.Vehicle;
import com.renault.garage.events.VehicleEventPublisher;
import com.renault.garage.exceptions.BusinessException;
import com.renault.garage.mappers.VehicleMapper;
import com.renault.garage.repositories.GarageRepository;
import com.renault.garage.repositories.VehicleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VehicleServiceImplTest {

    @Mock
    private VehicleRepository vehicleRepository;
    @Mock
    private GarageRepository garageRepository;
    @Mock
    private VehicleMapper vehicleMapper;

    @InjectMocks
    private VehicleServiceImpl vehicleService;

    @Mock
    private VehicleEventPublisher vehicleEventPublisher;

    private VehicleDTO vehicleDTO;
    private Vehicle vehicle;
    private Garage garage;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        vehicleDTO = new VehicleDTO(
                null,
                "Clio V",
                "Renault",
                2020,
                "Diesel",
                null,
                new ArrayList<>()
        );

        vehicle = new Vehicle();
        vehicle.setId(1L);

        garage = new Garage();
        garage.setId(10L);
        garage.setVehicles(new ArrayList<>());
    }


    @Test
    void testAddVehicleToGarage_success() {
        when(garageRepository.findById(10L)).thenReturn(Optional.of(garage));
        when(vehicleMapper.toEntity(vehicleDTO)).thenReturn(vehicle);
        when(vehicleRepository.save(vehicle)).thenReturn(vehicle);
        when(vehicleMapper.toDto(vehicle)).thenReturn(vehicleDTO);

        VehicleDTO result = vehicleService.addVehicleToGarage(10L, vehicleDTO);

        assertNotNull(result);
        verify(vehicleRepository).save(vehicle);
        assertEquals("Renault", result.brand());
        assertEquals(garage, vehicle.getGarage());
    }

    @Test
    void testAddVehicleToGarage_throwsWhenGarageNotFound() {
        when(garageRepository.findById(99L)).thenReturn(Optional.empty());

        BusinessException ex = assertThrows(BusinessException.class,
                () -> vehicleService.addVehicleToGarage(99L, vehicleDTO));

        assertEquals("Garage not found", ex.getMessage());
    }

    @Test
    void testAddVehicleToGarage_whenLimitReached() {
        List<Vehicle> fiftyVehicles = Collections.nCopies(50, new Vehicle());
        garage.setVehicles(fiftyVehicles);

        when(garageRepository.findById(10L)).thenReturn(Optional.of(garage));

        BusinessException ex = assertThrows(BusinessException.class,
                () -> vehicleService.addVehicleToGarage(10L, vehicleDTO));

        assertEquals("Garage vehicle limit reached (50)", ex.getMessage());
    }

    @Test
    void testUpdateVehicle_success() {
        when(vehicleRepository.findById(1L)).thenReturn(Optional.of(vehicle));
        when(vehicleRepository.save(vehicle)).thenReturn(vehicle);
        when(vehicleMapper.toDto(vehicle)).thenReturn(vehicleDTO);

        VehicleDTO result = vehicleService.updateVehicle(1L, vehicleDTO);

        assertNotNull(result);
        assertEquals("Renault", vehicle.getBrand());
        verify(vehicleRepository).save(vehicle);
    }

    @Test
    void testUpdateVehicle_vehicleNotFound() {
        when(vehicleRepository.findById(1L)).thenReturn(Optional.empty());

        BusinessException ex = assertThrows(BusinessException.class,
                () -> vehicleService.updateVehicle(1L, vehicleDTO));

        assertEquals("Vehicle not found", ex.getMessage());
    }

    @Test
    void testDeleteVehicle() {
        vehicleService.deleteVehicle(1L);
        verify(vehicleRepository).deleteById(1L);
    }

    @Test
    void testGetVehiclesByGarage() {
        when(vehicleRepository.findByGarageId(10L)).thenReturn(List.of(vehicle));
        when(vehicleMapper.toDto(vehicle)).thenReturn(vehicleDTO);

        List<VehicleDTO> result = vehicleService.getVehiclesByGarage(10L);

        assertEquals(1, result.size());
        verify(vehicleRepository).findByGarageId(10L);
    }
}

