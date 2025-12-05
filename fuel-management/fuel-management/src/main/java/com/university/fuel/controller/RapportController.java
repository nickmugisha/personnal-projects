// src/main/java/com/university/fuel/controller/RapportController.java
package com.university.fuel.controller;

import com.university.fuel.entity.DemandeCarburant;
import com.university.fuel.entity.StockCarburant;
import com.university.fuel.entity.Approvisionnement;
import com.university.fuel.service.DemandeCarburantService;
import com.university.fuel.service.StockCarburantService;
import com.university.fuel.service.ApprovisionnementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/rapports")
@CrossOrigin(origins = "*")
public class RapportController {

    @Autowired
    private DemandeCarburantService demandeService;

    @Autowired
    private StockCarburantService stockService;

    @Autowired
    private ApprovisionnementService approvisionnementService;

    @GetMapping("/consommation")
    @PreAuthorize("hasAnyRole('DAF', 'ADMIN')")
    public ResponseEntity<Map<String, Object>> getRapportConsommation() {
        List<DemandeCarburant> demandesApprouvees = demandeService.getDemandesByStatut(com.university.fuel.entity.StatutDemande.APPROUVEE);
        
        double totalConsommation = demandesApprouvees.stream()
                .mapToDouble(DemandeCarburant::getQuantiteDemandee)
                .sum();
        
        Map<String, Object> rapport = new HashMap<>();
        rapport.put("totalConsommation", totalConsommation);
        rapport.put("nombreDemandesApprouvees", demandesApprouvees.size());
        rapport.put("demandes", demandesApprouvees);
        
        return ResponseEntity.ok(rapport);
    }

    @GetMapping("/stock")
    @PreAuthorize("hasAnyRole('DAF', 'RESPONSABLE_CARBURANT', 'ADMIN')")
    public ResponseEntity<List<StockCarburant>> getRapportStock() {
        List<StockCarburant> stocks = stockService.getAllStocks();
        return ResponseEntity.ok(stocks);
    }

    @GetMapping("/approvisionnements")
    @PreAuthorize("hasAnyRole('DAF', 'ADMIN')")
    public ResponseEntity<List<Approvisionnement>> getRapportApprovisionnements() {
        List<Approvisionnement> approvisionnements = approvisionnementService.getAllApprovisionnements();
        return ResponseEntity.ok(approvisionnements);
    }

    @GetMapping("/activite")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> getRapportActivite() {
        List<DemandeCarburant> toutesDemandes = demandeService.getAllDemandes();
        List<Approvisionnement> tousApprovisionnements = approvisionnementService.getAllApprovisionnements();
        
        Map<String, Object> rapport = new HashMap<>();
        rapport.put("totalDemandes", toutesDemandes.size());
        rapport.put("totalApprovisionnements", tousApprovisionnements.size());
        rapport.put("demandesParStatut", 
            toutesDemandes.stream()
                .collect(java.util.stream.Collectors.groupingBy(
                    DemandeCarburant::getStatut, 
                    java.util.stream.Collectors.counting()
                ))
        );
        
        return ResponseEntity.ok(rapport);
    }
}