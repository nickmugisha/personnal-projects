// src/main/java/com/university/fuel/service/DemandeCarburantService.java
package com.university.fuel.service;

import com.university.fuel.entity.*;
import com.university.fuel.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class DemandeCarburantService {

    @Autowired
    private DemandeCarburantRepository demandeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EnginRepository enginRepository;

    @Autowired
    private StockCarburantRepository stockRepository;

    public DemandeCarburant createDemande(DemandeCarburant demande, Long chauffeurId) {
        User chauffeur = userRepository.findById(chauffeurId)
                .orElseThrow(() -> new RuntimeException("Chauffeur not found"));
        
        Engin engin = enginRepository.findById(demande.getEngin().getId())
                .orElseThrow(() -> new RuntimeException("Engin not found"));
        
        demande.setChauffeur(chauffeur);
        demande.setEngin(engin);
        demande.setStatut(StatutDemande.EN_ATTENTE_CHEF);
        demande.setValidationChef(false);
        demande.setValidationResponsable(false);
        demande.setValidationDAF(false);
        
        return demandeRepository.save(demande);
    }

    public List<DemandeCarburant> getDemandesByChauffeur(Long chauffeurId) {
        return demandeRepository.findByChauffeurId(chauffeurId);
    }

    public List<DemandeCarburant> getDemandesEnAttenteChef() {
        return demandeRepository.findByValidationChefFalse();
    }

    public List<DemandeCarburant> getDemandesEnAttenteResponsable() {
        return demandeRepository.findByValidationChefTrueAndValidationResponsableFalse();
    }

    public List<DemandeCarburant> getDemandesEnAttenteDAF() {
        return demandeRepository.findByValidationResponsableTrueAndValidationDAFFalse();
    }

    public DemandeCarburant validerParChef(Long demandeId) {
        DemandeCarburant demande = demandeRepository.findById(demandeId)
                .orElseThrow(() -> new RuntimeException("Demande not found"));
        
        demande.setValidationChef(true);
        demande.setStatut(StatutDemande.EN_ATTENTE_RESPONSABLE);
        
        return demandeRepository.save(demande);
    }

    public DemandeCarburant rejeterParChef(Long demandeId, String raison) {
        DemandeCarburant demande = demandeRepository.findById(demandeId)
                .orElseThrow(() -> new RuntimeException("Demande not found"));
        
        demande.setValidationChef(false);
        demande.setStatut(StatutDemande.REJETEE);
        demande.setRaisonRejet(raison);
        
        return demandeRepository.save(demande);
    }

    public DemandeCarburant validerParResponsable(Long demandeId) {
        DemandeCarburant demande = demandeRepository.findById(demandeId)
                .orElseThrow(() -> new RuntimeException("Demande not found"));
        
        // Check stock availability
        StockCarburant stock = stockRepository.findByCarburant(demande.getEngin().getTypeCarburant())
                .orElseThrow(() -> new RuntimeException("Stock not found for this carburant type"));
        
        if (stock.getQuantite() < demande.getQuantiteDemandee()) {
            throw new RuntimeException("Stock insuffisant");
        }
        
        demande.setValidationResponsable(true);
        demande.setStatut(StatutDemande.EN_ATTENTE_DAF);
        
        return demandeRepository.save(demande);
    }

    public DemandeCarburant rejeterParResponsable(Long demandeId, String raison) {
        DemandeCarburant demande = demandeRepository.findById(demandeId)
                .orElseThrow(() -> new RuntimeException("Demande not found"));
        
        demande.setValidationResponsable(false);
        demande.setStatut(StatutDemande.REJETEE);
        demande.setRaisonRejet(raison);
        
        return demandeRepository.save(demande);
    }

    @Transactional
    public DemandeCarburant validerParDAF(Long demandeId) {
        DemandeCarburant demande = demandeRepository.findById(demandeId)
                .orElseThrow(() -> new RuntimeException("Demande not found"));
        
        // Update stock
        StockCarburant stock = stockRepository.findByCarburant(demande.getEngin().getTypeCarburant())
                .orElseThrow(() -> new RuntimeException("Stock not found for this carburant type"));
        
        stock.setQuantite(stock.getQuantite() - demande.getQuantiteDemandee());
        stockRepository.save(stock);
        
        demande.setValidationDAF(true);
        demande.setStatut(StatutDemande.APPROUVEE);
        
        return demandeRepository.save(demande);
    }

    public DemandeCarburant rejeterParDAF(Long demandeId, String raison) {
        DemandeCarburant demande = demandeRepository.findById(demandeId)
                .orElseThrow(() -> new RuntimeException("Demande not found"));
        
        demande.setValidationDAF(false);
        demande.setStatut(StatutDemande.REJETEE);
        demande.setRaisonRejet(raison);
        
        return demandeRepository.save(demande);
    }

    public List<DemandeCarburant> getAllDemandes() {
        return demandeRepository.findAll();
    }

    public List<DemandeCarburant> getDemandesByStatut(StatutDemande statut) {
        return demandeRepository.findByStatut(statut);
    }
}