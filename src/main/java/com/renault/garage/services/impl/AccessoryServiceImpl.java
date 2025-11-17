package com.renault.garage.services.impl;

import com.renault.garage.dtos.AccessoryDTO;
import com.renault.garage.entities.Accessory;
import com.renault.garage.entities.Vehicle;
import com.renault.garage.exceptions.BusinessException;
import com.renault.garage.mappers.AccessoryMapper;
import com.renault.garage.repositories.AccessoryRepository;
import com.renault.garage.repositories.VehicleRepository;
import com.renault.garage.services.AccessoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AccessoryServiceImpl implements AccessoryService {

    private final AccessoryRepository accessoryRepository;
    private final VehicleRepository vehicleRepository;
    private final AccessoryMapper accessoryMapper;

    @Override
    public AccessoryDTO addAccessoryToVehicle(Long vehicleId, AccessoryDTO accessoryDTO) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new BusinessException("Vehicle not found"));
        Accessory accessory = accessoryMapper.toEntity(accessoryDTO);
        accessory.setVehicle(vehicle);
        return accessoryMapper.toDto(accessoryRepository.save(accessory));
    }

    @Override
    public AccessoryDTO updateAccessory(Long id, AccessoryDTO accessoryDTO) {
        Accessory existing = accessoryRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Accessory not found"));
        existing.setName(accessoryDTO.name());
        existing.setDescription(accessoryDTO.description());
        existing.setPrice(accessoryDTO.price());
        existing.setType(accessoryDTO.type());
        return accessoryMapper.toDto(accessoryRepository.save(existing));
    }

    @Override
    public void deleteAccessory(Long id) {
        accessoryRepository.deleteById(id);
    }

}
