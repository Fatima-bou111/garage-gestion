package com.renault.garage.controllers;


import com.renault.garage.dtos.AccessoryDTO;
import com.renault.garage.services.impl.AccessoryServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class AccessoryController {
    private final AccessoryServiceImpl service;
    public AccessoryController(AccessoryServiceImpl service){ this.service = service; }

    @PostMapping("/vehicles/{vehicleId}/accessories")
    public ResponseEntity<AccessoryDTO> addAccessory(@PathVariable Long vehicleId, @Valid @RequestBody AccessoryDTO a){
        return ResponseEntity.ok(service.addAccessoryToVehicle(vehicleId, a));
    }

    @PutMapping("/accessories/{id}")
    public ResponseEntity<AccessoryDTO> update(@PathVariable Long id, @Valid @RequestBody AccessoryDTO a){
        return ResponseEntity.ok(service.updateAccessory(id, a));
    }

    @DeleteMapping("/accessories/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.deleteAccessory(id);
        return ResponseEntity.noContent().build();
    }
}
