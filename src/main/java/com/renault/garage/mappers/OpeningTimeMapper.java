package com.renault.garage.mappers;


import com.renault.garage.dtos.OpeningTimeDTO;
import com.renault.garage.entities.OpeningTime;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Mapper(componentModel = "spring")
public interface OpeningTimeMapper {
    List<OpeningTimeDTO> toDtoList(List<OpeningTime> list);
    List<OpeningTime> toEntityList(List<OpeningTimeDTO> list);

    DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("HH:mm");

    @Mapping(target = "startTime", source = "startTime", qualifiedByName = "localTimeToString")
    @Mapping(target = "endTime", source = "endTime", qualifiedByName = "localTimeToString")
    OpeningTimeDTO toDto(OpeningTime entity);

    @Mapping(target = "startTime", source = "startTime", qualifiedByName = "stringToLocalTime")
    @Mapping(target = "endTime", source = "endTime", qualifiedByName = "stringToLocalTime")
    OpeningTime toEntity(OpeningTimeDTO dto);

    @Named("localTimeToString")
    static String localTimeToString(LocalTime time) {
        return time == null ? null : time.format(FORMAT);
    }

    @Named("stringToLocalTime")
    static LocalTime stringToLocalTime(String s) {
        return (s == null || s.isBlank()) ? null : LocalTime.parse(s, FORMAT);
    }
}




