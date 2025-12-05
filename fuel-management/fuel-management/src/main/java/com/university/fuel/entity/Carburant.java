// src/main/java/com/university/fuel/entity/Carburant.java
package com.university.fuel.entity;

import jakarta.persistence.*;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "carburants")
public class Carburant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String type; // ESSENCE, MAZOUT

    private String description;

    @OneToMany(mappedBy = "typeCarburant")
    @JsonIgnore
    private List<Engin> engins;

    @OneToMany(mappedBy = "carburant")
    @JsonIgnore
    private List<StockCarburant> stocks;

    // Constructors, Getters, Setters
    public Carburant() {}

    public Carburant(String type, String description) {
        this.type = type;
        this.description = description;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public List<Engin> getEngins() { return engins; }
    public void setEngins(List<Engin> engins) { this.engins = engins; }
    public List<StockCarburant> getStocks() { return stocks; }
    public void setStocks(List<StockCarburant> stocks) { this.stocks = stocks; }
}