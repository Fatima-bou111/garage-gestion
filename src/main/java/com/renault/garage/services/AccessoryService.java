package com.renault.garage.services;

import com.renault.garage.dtos.AccessoryDTO;


public interface AccessoryService {
    AccessoryDTO addAccessoryToVehicle(Long vehicleId, AccessoryDTO accessoryDTO);
    AccessoryDTO updateAccessory(Long id, AccessoryDTO accessoryDTO);
    void deleteAccessory(Long id);
}
