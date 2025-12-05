// src/main/java/com/university/fuel/controller/EnginController.java
package com.university.fuel.controller;

import com.university.fuel.entity.Engin;
import com.university.fuel.service.EnginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/engins")
@CrossOrigin(origins = "*")
public class EnginController {

    @Autowired
    private EnginService enginService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createEngin(@RequestBody Engin engin) {
        try {
            Engin createdEngin = enginService.createEngin(engin);
            return ResponseEntity.ok(createdEngin);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'RESPONSABLE_CARBURANT')")
    public ResponseEntity<List<Engin>> getAllEngins() {
        List<Engin> engins = enginService.getAllEngins();
        return ResponseEntity.ok(engins);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'RESPONSABLE_CARBURANT')")
    public ResponseEntity<Engin> getEnginById(@PathVariable Long id) {
        Optional<Engin> engin = enginService.getEnginById(id);
        return engin.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateEngin(@PathVariable Long id, @RequestBody Engin enginDetails) {
        try {
            Engin updatedEngin = enginService.updateEngin(id, enginDetails);
            return ResponseEntity.ok(updatedEngin);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteEngin(@PathVariable Long id) {
        try {
            enginService.deleteEngin(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}