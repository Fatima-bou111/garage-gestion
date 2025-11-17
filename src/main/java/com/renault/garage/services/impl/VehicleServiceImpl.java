package com.renault.garage.services.impl;

import com.renault.garage.dtos.VehicleDTO;
import com.renault.garage.entities.Garage;
import com.renault.garage.entities.Vehicle;
import com.renault.garage.events.VehicleEvent;
import com.renault.garage.events.VehicleEventPublisher;
import com.renault.garage.exceptions.BusinessException;
import com.renault.garage.mappers.VehicleMapper;
import com.renault.garage.repositories.GarageRepository;
import com.renault.garage.repositories.VehicleRepository;
import com.renault.garage.services.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;
    private final GarageRepository garageRepository;
    private final VehicleMapper vehicleMapper;
    private final VehicleEventPublisher vehicleEventPublisher;


    @Override
    public VehicleDTO addVehicleToGarage(Long garageId, VehicleDTO vehicleDTO) {
        Garage garage = garageRepository.findById(garageId)
                .orElseThrow(() -> new BusinessException("Garage not found"));

        // Règle métier : max 50 véhicules
        if (garage.getVehicles().size() >= 50) {
            throw new BusinessException("Garage vehicle limit reached (50)");
        }

        Vehicle vehicle = vehicleMapper.toEntity(vehicleDTO);
        vehicle.setGarage(garage);
        VehicleEvent event = new VehicleEvent(vehicle.getId(), vehicle.getModel(), "CREATED", Instant.now());
        vehicleEventPublisher.publishVehicleCreated(event);
        return vehicleMapper.toDto(vehicleRepository.save(vehicle));
    }

    @Override
    public VehicleDTO updateVehicle(Long id, VehicleDTO vehicleDTO) {
        Vehicle existing = vehicleRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Vehicle not found"));

        existing.setBrand(vehicleDTO.brand());
        existing.setYear(vehicleDTO.year());
        existing.setFuelType(vehicleDTO.fuelType());
        return vehicleMapper.toDto(vehicleRepository.save(existing));
    }

    @Override
    public void deleteVehicle(Long id) {
        vehicleRepository.deleteById(id);
    }

    @Override
    public List<VehicleDTO> getVehiclesByGarage(Long garageId) {
        return vehicleRepository.findByGarageId(garageId)
                .stream().map(vehicleMapper::toDto)
                .collect(Collectors.toList());
    }

}
