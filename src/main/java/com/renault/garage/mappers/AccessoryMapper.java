package com.renault.garage.mappers;


import com.renault.garage.dtos.AccessoryDTO;
import com.renault.garage.entities.Accessory;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AccessoryMapper {
    AccessoryMapper INSTANCE = Mappers.getMapper(AccessoryMapper.class);

    AccessoryDTO toDto(Accessory entity);
    Accessory toEntity(AccessoryDTO dto);
}

