package com.renault.garage.controllers;


import com.renault.garage.dtos.GarageDTO;
import com.renault.garage.services.impl.GarageServiceImpl;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/garages")
public class GarageController {
    private final GarageServiceImpl service;
    public GarageController(GarageServiceImpl service){ this.service=service; }

    @PostMapping
    public ResponseEntity<GarageDTO> create(@Valid @RequestBody GarageDTO g){
        return ResponseEntity.ok(service.createGarage(g));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GarageDTO> update(@PathVariable Long id, @Valid @RequestBody GarageDTO g){
        return ResponseEntity.ok(service.updateGarage(id, g));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.deleteGarage(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<GarageDTO>> getById(@PathVariable Long id){
        return ResponseEntity.ok(service.getGarageById(id));
    }

    @GetMapping
    public ResponseEntity<Page<GarageDTO>> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "ASC") String dir) {

        Sort.Direction direction = Sort.Direction.fromString(dir);
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        return ResponseEntity.ok(service.getAllGarages(pageable));
    }
}

