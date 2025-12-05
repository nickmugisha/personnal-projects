// src/main/java/com/university/fuel/service/StockCarburantService.java
package com.university.fuel.service;

import com.university.fuel.entity.StockCarburant;
import com.university.fuel.entity.Carburant;
import com.university.fuel.repository.StockCarburantRepository;
import com.university.fuel.repository.CarburantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class StockCarburantService {

    @Autowired
    private StockCarburantRepository stockRepository;

    @Autowired
    private CarburantRepository carburantRepository;

    public List<StockCarburant> getAllStocks() {
        return stockRepository.findAll();
    }

    public Optional<StockCarburant> getStockByCarburantId(Long carburantId) {
        return stockRepository.findByCarburantId(carburantId);
    }

    public StockCarburant updateStock(Long carburantId, Double nouvelleQuantite) {
        Carburant carburant = carburantRepository.findById(carburantId)
                .orElseThrow(() -> new RuntimeException("Carburant not found"));
        
        StockCarburant stock = stockRepository.findByCarburant(carburant)
                .orElse(new StockCarburant(carburant, 0.0));
        
        stock.setQuantite(nouvelleQuantite);
        return stockRepository.save(stock);
    }

    public StockCarburant approvisionner(Long carburantId, Double quantite) {
        Carburant carburant = carburantRepository.findById(carburantId)
                .orElseThrow(() -> new RuntimeException("Carburant not found"));
        
        StockCarburant stock = stockRepository.findByCarburant(carburant)
                .orElse(new StockCarburant(carburant, 0.0));
        
        stock.setQuantite(stock.getQuantite() + quantite);
        return stockRepository.save(stock);
    }
}