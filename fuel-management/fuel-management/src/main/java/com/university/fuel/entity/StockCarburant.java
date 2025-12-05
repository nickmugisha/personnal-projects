// src/main/java/com/university/fuel/entity/StockCarburant.java
package com.university.fuel.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "stock_carburant")
public class StockCarburant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "carburant_id")
    private Carburant carburant;

    private Double quantite;
    private LocalDateTime dateMiseAJour;

    // Constructors, Getters, Setters
    public StockCarburant() {}

    public StockCarburant(Carburant carburant, Double quantite) {
        this.carburant = carburant;
        this.quantite = quantite;
        this.dateMiseAJour = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Carburant getCarburant() { return carburant; }
    public void setCarburant(Carburant carburant) { this.carburant = carburant; }
    public Double getQuantite() { return quantite; }
    public void setQuantite(Double quantite) { this.quantite = quantite; }
    public LocalDateTime getDateMiseAJour() { return dateMiseAJour; }
    public void setDateMiseAJour(LocalDateTime dateMiseAJour) { this.dateMiseAJour = dateMiseAJour; }
}