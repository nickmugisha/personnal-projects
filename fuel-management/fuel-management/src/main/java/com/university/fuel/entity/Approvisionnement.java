// src/main/java/com/university/fuel/entity/Approvisionnement.java
package com.university.fuel.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "approvisionnements")
public class Approvisionnement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "carburant_id")
    private Carburant carburant;

    private Double quantite;
    private String source;
    private LocalDateTime dateApprovisionnement;
    private String numeroBon;

    // Constructors, Getters, Setters
    public Approvisionnement() {}

    public Approvisionnement(Carburant carburant, Double quantite, String source, String numeroBon) {
        this.carburant = carburant;
        this.quantite = quantite;
        this.source = source;
        this.numeroBon = numeroBon;
        this.dateApprovisionnement = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Carburant getCarburant() { return carburant; }
    public void setCarburant(Carburant carburant) { this.carburant = carburant; }
    public Double getQuantite() { return quantite; }
    public void setQuantite(Double quantite) { this.quantite = quantite; }
    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }
    public LocalDateTime getDateApprovisionnement() { return dateApprovisionnement; }
    public void setDateApprovisionnement(LocalDateTime dateApprovisionnement) { this.dateApprovisionnement = dateApprovisionnement; }
    public String getNumeroBon() { return numeroBon; }
    public void setNumeroBon(String numeroBon) { this.numeroBon = numeroBon; }
}