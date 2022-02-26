package com.elasticsearch.controller;

import com.elasticsearch.document.Person;
import com.elasticsearch.document.Vehicle;
import com.elasticsearch.service.PersonService;
import com.elasticsearch.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vehicle")
@RequiredArgsConstructor
public class VehicleController {
    private final Logger log = LoggerFactory.getLogger(VehicleController.class);

    private final VehicleService vehicleService;

    @PostMapping
    public void createDocumentToIndex(@RequestBody final Vehicle vehicle) {
        log.info("vehicle = {}", vehicle);
        vehicleService.createDocumentToIndex(vehicle);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vehicle> getById(@PathVariable final String id) {
        log.info("id = {}", id);
        return new ResponseEntity<>(vehicleService.getById(id), HttpStatus.OK);
    }
}
