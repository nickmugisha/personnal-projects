package com.university.fuel.entity;

import jakarta.persistence.*;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "engins")
public class Engin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type; // Voiture, Moto, Groupe électrogène
    private String plaque;
    private Double consommationMoyenne;

    @ManyToOne
    @JoinColumn(name = "carburant_id")
    private Carburant typeCarburant;

    @OneToMany(mappedBy = "engin")
    @JsonIgnore
    private List<DemandeCarburant> demandes;

    // Constructors, Getters, Setters
    public Engin() {}

    public Engin(String type, String plaque, Double consommationMoyenne, Carburant typeCarburant) {
        this.type = type;
        this.plaque = plaque;
        this.consommationMoyenne = consommationMoyenne;
        this.typeCarburant = typeCarburant;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getPlaque() { return plaque; }
    public void setPlaque(String plaque) { this.plaque = plaque; }
    public Double getConsommationMoyenne() { return consommationMoyenne; }
    public void setConsommationMoyenne(Double consommationMoyenne) { this.consommationMoyenne = consommationMoyenne; }
    public Carburant getTypeCarburant() { return typeCarburant; }
    public void setTypeCarburant(Carburant typeCarburant) { this.typeCarburant = typeCarburant; }
    public List<DemandeCarburant> getDemandes() { return demandes; }
    public void setDemandes(List<DemandeCarburant> demandes) { this.demandes = demandes; }
}