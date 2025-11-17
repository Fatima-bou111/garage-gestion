package com.renault.garage.controllers;

import com.renault.garage.dtos.VehicleDTO;
import com.renault.garage.services.impl.VehicleServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class VehicleController {
    private final VehicleServiceImpl service;
    public VehicleController(VehicleServiceImpl service) { this.service = service; }

    @PostMapping("/garages/{garageId}/vehicles")
    public ResponseEntity<VehicleDTO> addVehicle(@PathVariable Long garageId, @Valid @RequestBody VehicleDTO v){
        return ResponseEntity.ok(service.addVehicleToGarage(garageId, v));
    }

    @PutMapping("/vehicles/{id}")
    public ResponseEntity<VehicleDTO> updateVehicle(@PathVariable Long id, @Valid @RequestBody VehicleDTO v){
        return ResponseEntity.ok(service.updateVehicle(id, v));
    }

    @DeleteMapping("/vehicles/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long id){
        service.deleteVehicle(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/garages/{garageId}/vehicles")
    public ResponseEntity<List<VehicleDTO>> listVehiclesOfGarage(@PathVariable Long garageId) {
        return ResponseEntity.ok(service.getVehiclesByGarage(garageId));
    }
}

