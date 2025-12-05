// src/main/java/com/university/fuel/controller/DemandeCarburantController.java
package com.university.fuel.controller;

import com.university.fuel.entity.DemandeCarburant;
import com.university.fuel.entity.StatutDemande;
import com.university.fuel.service.DemandeCarburantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/demandes")
@CrossOrigin(origins = "*")
public class DemandeCarburantController {

    @Autowired
    private DemandeCarburantService demandeService;

    // CHAUFFEUR Endpoints
    @PostMapping
    @PreAuthorize("hasRole('CHAUFFEUR')")
    public ResponseEntity<?> createDemande(@RequestBody DemandeCarburant demande) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String email = auth.getName();
            // In a real app, you'd get user ID from JWT token
            // For now, we'll use a placeholder - you'd need to implement user context service
            Long chauffeurId = 1L; // This should come from authenticated user
            
            DemandeCarburant createdDemande = demandeService.createDemande(demande, chauffeurId);
            return ResponseEntity.ok(createdDemande);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/mes-demandes")
    @PreAuthorize("hasRole('CHAUFFEUR')")
    public ResponseEntity<List<DemandeCarburant>> getMesDemandes() {
        Long chauffeurId = 1L; // This should come from authenticated user
        List<DemandeCarburant> demandes = demandeService.getDemandesByChauffeur(chauffeurId);
        return ResponseEntity.ok(demandes);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('CHAUFFEUR')")
    public ResponseEntity<DemandeCarburant> getDemandeById(@PathVariable Long id) {
        // Add authorization check to ensure user can only access their own demandes
        return ResponseEntity.notFound().build(); // Implementation needed
    }

    // CHEF CHALOI Endpoints
    @GetMapping("/en-attente-chef")
    @PreAuthorize("hasRole('CHEF_CHALOI')")
    public ResponseEntity<List<DemandeCarburant>> getDemandesEnAttenteChef() {
        List<DemandeCarburant> demandes = demandeService.getDemandesEnAttenteChef();
        return ResponseEntity.ok(demandes);
    }

    @PutMapping("/{id}/valider-chef")
    @PreAuthorize("hasRole('CHEF_CHALOI')")
    public ResponseEntity<?> validerParChef(@PathVariable Long id) {
        try {
            DemandeCarburant demande = demandeService.validerParChef(id);
            return ResponseEntity.ok(demande);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}/rejeter-chef")
    @PreAuthorize("hasRole('CHEF_CHALOI')")
    public ResponseEntity<?> rejeterParChef(@PathVariable Long id, @RequestBody String raison) {
        try {
            DemandeCarburant demande = demandeService.rejeterParChef(id, raison);
            return ResponseEntity.ok(demande);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // RESPONSABLE CARBURANT Endpoints
    @GetMapping("/en-attente-responsable")
    @PreAuthorize("hasRole('RESPONSABLE_CARBURANT')")
    public ResponseEntity<List<DemandeCarburant>> getDemandesEnAttenteResponsable() {
        List<DemandeCarburant> demandes = demandeService.getDemandesEnAttenteResponsable();
        return ResponseEntity.ok(demandes);
    }

    @PutMapping("/{id}/valider-responsable")
    @PreAuthorize("hasRole('RESPONSABLE_CARBURANT')")
    public ResponseEntity<?> validerParResponsable(@PathVariable Long id) {
        try {
            DemandeCarburant demande = demandeService.validerParResponsable(id);
            return ResponseEntity.ok(demande);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}/rejeter-responsable")
    @PreAuthorize("hasRole('RESPONSABLE_CARBURANT')")
    public ResponseEntity<?> rejeterParResponsable(@PathVariable Long id, @RequestBody String raison) {
        try {
            DemandeCarburant demande = demandeService.rejeterParResponsable(id, raison);
            return ResponseEntity.ok(demande);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // DAF Endpoints
    @GetMapping("/en-attente-daf")
    @PreAuthorize("hasRole('DAF')")
    public ResponseEntity<List<DemandeCarburant>> getDemandesEnAttenteDAF() {
        List<DemandeCarburant> demandes = demandeService.getDemandesEnAttenteDAF();
        return ResponseEntity.ok(demandes);
    }

    @PutMapping("/{id}/valider-daf")
    @PreAuthorize("hasRole('DAF')")
    public ResponseEntity<?> validerParDAF(@PathVariable Long id) {
        try {
            DemandeCarburant demande = demandeService.validerParDAF(id);
            return ResponseEntity.ok(demande);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}/rejeter-daf")
    @PreAuthorize("hasRole('DAF')")
    public ResponseEntity<?> rejeterParDAF(@PathVariable Long id, @RequestBody String raison) {
        try {
            DemandeCarburant demande = demandeService.rejeterParDAF(id, raison);
            return ResponseEntity.ok(demande);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Global Consultation Endpoints
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'DAF')")
    public ResponseEntity<List<DemandeCarburant>> getAllDemandes() {
        List<DemandeCarburant> demandes = demandeService.getAllDemandes();
        return ResponseEntity.ok(demandes);
    }

    @GetMapping("/statut/{statut}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DAF', 'RESPONSABLE_CARBURANT')")
    public ResponseEntity<List<DemandeCarburant>> getDemandesByStatut(@PathVariable StatutDemande statut) {
        List<DemandeCarburant> demandes = demandeService.getDemandesByStatut(statut);
        return ResponseEntity.ok(demandes);
    }

    @GetMapping("/historique")
    @PreAuthorize("hasAnyRole('ADMIN', 'DAF')")
    public ResponseEntity<List<DemandeCarburant>> getHistorique() {
        List<DemandeCarburant> demandes = demandeService.getAllDemandes();
        return ResponseEntity.ok(demandes);
    }
}