package com.renault.garage.repositories;


import com.renault.garage.entities.Garage;
import com.renault.garage.enums.FuelType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GarageRepository extends JpaRepository<Garage, Long>, JpaSpecificationExecutor<Garage> {

    Page<Garage> findAll(Pageable pageable);

     Page<Garage> findDistinctByVehicles_FuelType(FuelType fuelType, Pageable pageable);
}


