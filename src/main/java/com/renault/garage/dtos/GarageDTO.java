package com.renault.garage.dtos;


import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;

public record GarageDTO(
        Long id,
        String name,
        String address,
        String telephone,
        String email,
        Map<DayOfWeek, List<OpeningTimeDTO>> horairesOuverture,
        List<VehicleDTO> vehicles
) {}

