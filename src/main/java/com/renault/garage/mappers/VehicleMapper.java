package com.renault.garage.mappers;


import com.renault.garage.dtos.VehicleDTO;
import com.renault.garage.entities.Vehicle;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {AccessoryMapper.class})
public interface VehicleMapper {
    VehicleMapper INSTANCE = Mappers.getMapper(VehicleMapper.class);

    VehicleDTO toDto(Vehicle entity);

    Vehicle toEntity(VehicleDTO dto);
}

