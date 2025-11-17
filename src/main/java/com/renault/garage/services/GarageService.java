package com.renault.garage.services;


import com.renault.garage.dtos.GarageDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface GarageService {
    GarageDTO createGarage(GarageDTO garageDTO);
    GarageDTO updateGarage(Long id, GarageDTO garageDTO);
    void deleteGarage(Long id);
    Optional<GarageDTO> getGarageById(Long id);
    Page<GarageDTO> getAllGarages(Pageable pageable);
}

