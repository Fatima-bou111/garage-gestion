package com.renault.garage.dtos;

import java.util.List;

public record VehicleDTO (
     Long id,
     String model,
     String brand,
     int year,
     String fuelType,
     Long garageId,
     List<AccessoryDTO> accessories
    )
{}

