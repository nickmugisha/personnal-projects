// src/main/java/com/university/fuel/controller/StockCarburantController.java
package com.university.fuel.controller;

import com.university.fuel.entity.StockCarburant;
import com.university.fuel.service.StockCarburantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/stocks")
@CrossOrigin(origins = "*")
public class StockCarburantController {

    @Autowired
    private StockCarburantService stockService;

    @GetMapping
    @PreAuthorize("hasAnyRole('RESPONSABLE_CARBURANT', 'DAF')")
    public ResponseEntity<List<StockCarburant>> getAllStocks() {
        List<StockCarburant> stocks = stockService.getAllStocks();
        return ResponseEntity.ok(stocks);
    }

    @PostMapping("/approvisionnement")
    @PreAuthorize("hasRole('RESPONSABLE_CARBURANT')")
    public ResponseEntity<?> approvisionnerStock(@RequestParam Long carburantId, @RequestParam Double quantite) {
        try {
            StockCarburant stock = stockService.approvisionner(carburantId, quantite);
            return ResponseEntity.ok(stock);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{carburantId}")
    @PreAuthorize("hasRole('RESPONSABLE_CARBURANT')")
    public ResponseEntity<?> updateStock(@PathVariable Long carburantId, @RequestParam Double quantite) {
        try {
            StockCarburant stock = stockService.updateStock(carburantId, quantite);
            return ResponseEntity.ok(stock);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}