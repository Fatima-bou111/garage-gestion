package com.renault.garage.mappers;

import com.renault.garage.dtos.GarageDTO;
import com.renault.garage.dtos.OpeningTimeDTO;
import com.renault.garage.entities.Garage;
import com.renault.garage.entities.OpeningTime;
import org.mapstruct.Mapper;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;

@Mapper(componentModel = "spring", uses = {OpeningTimeMapper.class })
public interface GarageMapper {

    GarageDTO toDto(Garage entity);

    Garage toEntity(GarageDTO dto);

    Map<DayOfWeek, List<OpeningTimeDTO>> openingTimesToDto(
            Map<DayOfWeek, List<OpeningTime>> times);

    Map<DayOfWeek, List<OpeningTime>> openingTimesToEntity(
            Map<DayOfWeek, List<OpeningTimeDTO>> times);
}


