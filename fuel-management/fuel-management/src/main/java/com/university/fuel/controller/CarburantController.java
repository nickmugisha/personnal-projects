// src/main/java/com/university/fuel/controller/CarburantController.java
package com.university.fuel.controller;

import com.university.fuel.entity.Carburant;
import com.university.fuel.service.CarburantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/carburants")
@CrossOrigin(origins = "*")
public class CarburantController {

    @Autowired
    private CarburantService carburantService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createCarburant(@RequestBody Carburant carburant) {
        try {
            Carburant createdCarburant = carburantService.createCarburant(carburant);
            return ResponseEntity.ok(createdCarburant);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Carburant>> getAllCarburants() {
        List<Carburant> carburants = carburantService.getAllCarburants();
        return ResponseEntity.ok(carburants);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Carburant> getCarburantById(@PathVariable Long id) {
        Optional<Carburant> carburant = carburantService.getCarburantById(id);
        return carburant.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateCarburant(@PathVariable Long id, @RequestBody Carburant carburantDetails) {
        try {
            Carburant updatedCarburant = carburantService.updateCarburant(id, carburantDetails);
            return ResponseEntity.ok(updatedCarburant);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteCarburant(@PathVariable Long id) {
        try {
            carburantService.deleteCarburant(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}