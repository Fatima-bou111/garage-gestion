package com.renault.garage.entities;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


@Converter
public class DayOpeningConverter implements AttributeConverter<Map<DayOfWeek, List<OpeningTime>>, String> {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        MAPPER.registerModule(new JavaTimeModule());
        MAPPER.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Override
    public String convertToDatabaseColumn(Map<DayOfWeek, List<OpeningTime>> attribute) {
        if (attribute == null) return null;
        try {
            return MAPPER.writeValueAsString(attribute);
        } catch (Exception e) {
            throw new IllegalStateException("Error converting opening times to JSON", e);
        }
    }

    @Override
    public Map<DayOfWeek, List<OpeningTime>> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isBlank()) return Map.of();
        try {
            TypeReference<Map<DayOfWeek, List<OpeningTime>>> tr = new TypeReference<>() {};
            return MAPPER.readValue(dbData, tr);
        } catch (Exception e) {
            throw new IllegalStateException("Error reading opening times JSON", e);
        }
    }
}
