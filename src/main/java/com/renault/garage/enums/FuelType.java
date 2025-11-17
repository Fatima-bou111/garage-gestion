package com.renault.garage.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public enum FuelType {

    GASOLINE("gasoline"),
    DIESEL("diesel"),
    ELECTRIC("electric"),
    HYBRID("hybrid");

    private final String value;

    @Override
    public String toString() {
        return value;
    }
}

