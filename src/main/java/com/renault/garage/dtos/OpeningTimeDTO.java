package com.renault.garage.dtos;

import java.time.LocalTime;

public record OpeningTimeDTO(
        LocalTime startTime,
        LocalTime endTime
) {}