// src/main/java/com/university/fuel/entity/DemandeCarburant.java
package com.university.fuel.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "demandes_carburant")
public class DemandeCarburant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "chauffeur_id")
    @JsonIgnore
    private User chauffeur;

    @ManyToOne
    
    @JoinColumn(name = "engin_id")
    private Engin engin;

    private Double quantiteDemandee;
    private LocalDateTime dateDemande;
    private String motif;

    @Enumerated(EnumType.STRING)
    private StatutDemande statut = StatutDemande.EN_ATTENTE_CHEF;

    private Boolean validationChef = false;
    private Boolean validationResponsable = false;
    private Boolean validationDAF = false;

    private String raisonRejet;

    // Constructors, Getters, Setters
    public DemandeCarburant() {}

    public DemandeCarburant(User chauffeur, Engin engin, Double quantiteDemandee, String motif) {
        this.chauffeur = chauffeur;
        this.engin = engin;
        this.quantiteDemandee = quantiteDemandee;
        this.motif = motif;
        this.dateDemande = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public User getChauffeur() { return chauffeur; }
    public void setChauffeur(User chauffeur) { this.chauffeur = chauffeur; }
    public Engin getEngin() { return engin; }
    public void setEngin(Engin engin) { this.engin = engin; }
    public Double getQuantiteDemandee() { return quantiteDemandee; }
    public void setQuantiteDemandee(Double quantiteDemandee) { this.quantiteDemandee = quantiteDemandee; }
    public LocalDateTime getDateDemande() { return dateDemande; }
    public void setDateDemande(LocalDateTime dateDemande) { this.dateDemande = dateDemande; }
    public String getMotif() { return motif; }
    public void setMotif(String motif) { this.motif = motif; }
    public StatutDemande getStatut() { return statut; }
    public void setStatut(StatutDemande statut) { this.statut = statut; }
    public Boolean getValidationChef() { return validationChef; }
    public void setValidationChef(Boolean validationChef) { this.validationChef = validationChef; }
    public Boolean getValidationResponsable() { return validationResponsable; }
    public void setValidationResponsable(Boolean validationResponsable) { this.validationResponsable = validationResponsable; }
    public Boolean getValidationDAF() { return validationDAF; }
    public void setValidationDAF(Boolean validationDAF) { this.validationDAF = validationDAF; }
    public String getRaisonRejet() { return raisonRejet; }
    public void setRaisonRejet(String raisonRejet) { this.raisonRejet = raisonRejet; }
}