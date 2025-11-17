package com.renault.garage.services.impl;



import com.renault.garage.dtos.GarageDTO;
import com.renault.garage.entities.Garage;
import com.renault.garage.events.VehicleEventPublisher;
import com.renault.garage.mappers.GarageMapper;
import com.renault.garage.repositories.GarageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;



@ExtendWith(MockitoExtension.class)
class GarageServiceImplTest {

    @Mock
    private GarageRepository garageRepository;

    @Mock
    private GarageMapper garageMapper;

    @InjectMocks
    private GarageServiceImpl service;

    @Mock
    private VehicleEventPublisher vehicleEventPublisher;

    @Test
    void createGarage_shouldReturnDto() {
        GarageDTO dto = mock(GarageDTO.class);
        Garage entity = mock(Garage.class);
        Garage saved = mock(Garage.class);
        GarageDTO resultDto = mock(GarageDTO.class);

        when(garageMapper.toEntity(dto)).thenReturn(entity);
        when(garageRepository.save(entity)).thenReturn(saved);
        when(garageMapper.toDto(saved)).thenReturn(resultDto);

        GarageDTO returned = service.createGarage(dto);

        assertSame(resultDto, returned);
        verify(garageMapper).toEntity(dto);
        verify(garageRepository).save(entity);
        verify(garageMapper).toDto(saved);
        verify(vehicleEventPublisher, atMostOnce()).publishVehicleCreated(any());
    }

    @Test
    void updateGarage_shouldModifyAndReturnDto() {
        Long id = 1L;
        GarageDTO dto = mock(GarageDTO.class);
        Garage existing = mock(Garage.class);
        Garage saved = mock(Garage.class);
        GarageDTO resultDto = mock(GarageDTO.class);

        when(garageRepository.findById(id)).thenReturn(Optional.of(existing));
        when(garageRepository.save(existing)).thenReturn(saved);
        when(garageMapper.toDto(saved)).thenReturn(resultDto);

        when(dto.name()).thenReturn("Nouveau nom");
        when(dto.address()).thenReturn("Nouvelle adresse");
        when(dto.telephone()).thenReturn("0123456789");
        when(dto.email()).thenReturn("mail@test.fr");
        when(dto.horairesOuverture()).thenReturn(null);
        when(garageMapper.openingTimesToEntity(any())).thenReturn(null);

        GarageDTO returned = service.updateGarage(id, dto);

        assertSame(resultDto, returned);
        verify(garageRepository).findById(id);
        verify(existing).setName("Nouveau nom");
        verify(existing).setAddress("Nouvelle adresse");
        verify(existing).setTelephone("0123456789");
        verify(existing).setEmail("mail@test.fr");
        verify(garageMapper).openingTimesToEntity(null);
        verify(garageRepository).save(existing);
        verify(garageMapper).toDto(saved);
    }

    @Test
    void getGarageById_shouldReturnOptionalDto() {
        Long id = 2L;
        Garage entity = mock(Garage.class);
        GarageDTO dto = mock(GarageDTO.class);

        when(garageRepository.findById(id)).thenReturn(Optional.of(entity));
        when(garageMapper.toDto(entity)).thenReturn(dto);

        Optional<GarageDTO> result = service.getGarageById(id);

        assertTrue(result.isPresent());
        assertSame(dto, result.get());
        verify(garageRepository).findById(id);
        verify(garageMapper).toDto(entity);
    }

    @Test
    void getAllGarages_shouldReturnPageOfDtos() {
        Garage entity = mock(Garage.class);
        GarageDTO dto = mock(GarageDTO.class);
        Page<Garage> page = new PageImpl<>(List.of(entity));

        when(garageRepository.findAll(any(Pageable.class))).thenReturn(page);
        when(garageMapper.toDto(entity)).thenReturn(dto);

        Page<GarageDTO> result = service.getAllGarages(Pageable.unpaged());

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertSame(dto, result.getContent().get(0));
        verify(garageRepository).findAll(any(Pageable.class));
        verify(garageMapper).toDto(entity);
    }

    @Test
    void deleteGarage_shouldCallRepository() {
        Long id = 3L;
        doNothing().when(garageRepository).deleteById(id);

        service.deleteGarage(id);

        verify(garageRepository).deleteById(id);
    }
}

