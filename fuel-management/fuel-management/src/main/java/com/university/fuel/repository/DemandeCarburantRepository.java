// src/main/java/com/university/fuel/repository/DemandeCarburantRepository.java
package com.university.fuel.repository;

import com.university.fuel.entity.DemandeCarburant;
import com.university.fuel.entity.StatutDemande;
import com.university.fuel.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DemandeCarburantRepository extends JpaRepository<DemandeCarburant, Long> {
    List<DemandeCarburant> findByChauffeur(User chauffeur);
    List<DemandeCarburant> findByStatut(StatutDemande statut);
    List<DemandeCarburant> findByChauffeurId(Long chauffeurId);
    List<DemandeCarburant> findByValidationChefFalse();
    List<DemandeCarburant> findByValidationChefTrueAndValidationResponsableFalse();
    List<DemandeCarburant> findByValidationResponsableTrueAndValidationDAFFalse();
}