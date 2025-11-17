package com.renault.garage.services.impl;

import com.renault.garage.dtos.GarageDTO;
import com.renault.garage.entities.Garage;
import com.renault.garage.exceptions.BusinessException;
import com.renault.garage.mappers.GarageMapper;
import com.renault.garage.repositories.GarageRepository;
import com.renault.garage.services.GarageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GarageServiceImpl implements GarageService {

    private final GarageRepository garageRepository;
    private final GarageMapper garageMapper;

    @Override
    public GarageDTO createGarage(GarageDTO garageDTO) {
        Garage entity = garageMapper.toEntity(garageDTO);
        return garageMapper.toDto(garageRepository.save(entity));
    }

    @Override
    public GarageDTO updateGarage(Long id, GarageDTO garageDTO) {
        Garage existing = garageRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Garage not found"));
        existing.setName(garageDTO.name());
        existing.setAddress(garageDTO.address());
        existing.setTelephone(garageDTO.telephone());
        existing.setEmail(garageDTO.email());
        existing.setHorairesOuverture(
                garageMapper.openingTimesToEntity(garageDTO.horairesOuverture())
        );
        return garageMapper.toDto(garageRepository.save(existing));
    }

    @Override
    public void deleteGarage(Long id) {
        garageRepository.deleteById(id);
    }

    @Override
    public Optional<GarageDTO> getGarageById(Long id) {
        return garageRepository.findById(id).map(garageMapper::toDto);
    }

    @Override
    public Page<GarageDTO> getAllGarages(Pageable pageable) {
        return garageRepository.findAll(pageable).map(garageMapper::toDto);
    }
}
