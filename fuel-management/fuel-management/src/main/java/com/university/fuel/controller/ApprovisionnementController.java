// src/main/java/com/university/fuel/controller/ApprovisionnementController.java
package com.university.fuel.controller;

import com.university.fuel.entity.Approvisionnement;
import com.university.fuel.service.ApprovisionnementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/approvisionnements")
@CrossOrigin(origins = "*")
public class ApprovisionnementController {

    @Autowired
    private ApprovisionnementService approvisionnementService;

    @PostMapping
    @PreAuthorize("hasRole('RESPONSABLE_CARBURANT')")
    public ResponseEntity<?> createApprovisionnement(@RequestBody Approvisionnement approvisionnement) {
        try {
            Approvisionnement createdApprovisionnement = approvisionnementService.createApprovisionnement(approvisionnement);
            return ResponseEntity.ok(createdApprovisionnement);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('RESPONSABLE_CARBURANT', 'DAF')")
    public ResponseEntity<List<Approvisionnement>> getAllApprovisionnements() {
        List<Approvisionnement> approvisionnements = approvisionnementService.getAllApprovisionnements();
        return ResponseEntity.ok(approvisionnements);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('RESPONSABLE_CARBURANT', 'DAF')")
    public ResponseEntity<Approvisionnement> getApprovisionnementById(@PathVariable Long id) {
        Optional<Approvisionnement> approvisionnement = approvisionnementService.getApprovisionnementById(id);
        return approvisionnement.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
