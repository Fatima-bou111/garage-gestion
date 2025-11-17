package com.renault.garage.services;

import com.renault.garage.dtos.VehicleDTO;

import java.util.List;

public interface VehicleService {
    VehicleDTO addVehicleToGarage(Long garageId, VehicleDTO vehicleDTO);
    VehicleDTO updateVehicle(Long id, VehicleDTO vehicleDTO);
    void deleteVehicle(Long id);
    List<VehicleDTO> getVehiclesByGarage(Long garageId);
}
