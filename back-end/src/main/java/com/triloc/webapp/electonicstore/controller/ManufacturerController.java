package com.triloc.webapp.electonicstore.controller;

import com.triloc.webapp.electonicstore.exception.ResourceNotFoundException;
import com.triloc.webapp.electonicstore.model.Manufacturer;
import com.triloc.webapp.electonicstore.repository.ManufacturerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manufacturer")
public class ManufacturerController {

    private final ManufacturerRepository manufacturerRepository;

    @Autowired
    public ManufacturerController(ManufacturerRepository manufacturerRepository) {
        this.manufacturerRepository = manufacturerRepository;
    }

    @GetMapping
    public List<Manufacturer> getAllManufacturers() {
        return manufacturerRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Manufacturer> getManufacturerById(@PathVariable Long id) throws ResourceNotFoundException {
        Manufacturer manufacturer = findManufacturerById(id);
        return ResponseEntity.ok(manufacturer);
    }

    @PostMapping
    public ResponseEntity<Manufacturer> createManufacturer(@RequestBody Manufacturer manufacturer) {
        Manufacturer savedManufacturer = manufacturerRepository.save(manufacturer);
        return new ResponseEntity<>(savedManufacturer, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Manufacturer> updateManufacturer(@PathVariable Long id,
                                                           @RequestBody Manufacturer manufacturerDetails) throws ResourceNotFoundException {
        Manufacturer manufacturer = findManufacturerById(id);

        manufacturer.setManufacturer_name(manufacturerDetails.getManufacturer_name());
        Manufacturer updatedManufacturer = manufacturerRepository.save(manufacturer);

        return ResponseEntity.ok(updatedManufacturer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteManufacturer(@PathVariable Long id) throws ResourceNotFoundException {
        Manufacturer manufacturer = findManufacturerById(id);
        manufacturerRepository.delete(manufacturer);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private Manufacturer findManufacturerById(Long id) throws ResourceNotFoundException {
        return manufacturerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Manufacturer not found for this id :: " + id));
    }
}
