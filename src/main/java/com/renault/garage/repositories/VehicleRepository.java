package com.renault.garage.repositories;


import com.renault.garage.entities.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    List<Vehicle> findByGarageId(Long garageId);
    long countByGarageId(Long garageId);
}

