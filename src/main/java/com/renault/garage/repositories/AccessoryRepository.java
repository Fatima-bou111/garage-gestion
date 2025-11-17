package com.renault.garage.repositories;


import com.renault.garage.entities.Accessory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccessoryRepository extends JpaRepository<Accessory, Long> {
    List<Accessory> findByVehicleId(Long vehicleId);
}

