package com.renault.garage.dtos;

import java.math.BigDecimal;

public record AccessoryDTO(
        Long id,
        String name,
        String description,
        BigDecimal price,
        String type
) {}

