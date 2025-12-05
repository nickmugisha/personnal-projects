// src/main/java/com/university/fuel/service/ApprovisionnementService.java
package com.university.fuel.service;

import com.university.fuel.entity.Approvisionnement;
import com.university.fuel.repository.ApprovisionnementRepository;
import com.university.fuel.repository.CarburantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ApprovisionnementService {

    @Autowired
    private ApprovisionnementRepository approvisionnementRepository;

    @Autowired
    private CarburantRepository carburantRepository;

    @Autowired
    private StockCarburantService stockService;

    public Approvisionnement createApprovisionnement(Approvisionnement approvisionnement) {
        // Verify carburant exists
        carburantRepository.findById(approvisionnement.getCarburant().getId())
                .orElseThrow(() -> new RuntimeException("Carburant type not found"));
        
        // Update stock
        stockService.approvisionner(approvisionnement.getCarburant().getId(), approvisionnement.getQuantite());
        
        return approvisionnementRepository.save(approvisionnement);
    }

    public List<Approvisionnement> getAllApprovisionnements() {
        return approvisionnementRepository.findAll();
    }

    public Optional<Approvisionnement> getApprovisionnementById(Long id) {
        return approvisionnementRepository.findById(id);
    }

    public List<Approvisionnement> getApprovisionnementsByCarburant(Long carburantId) {
        return approvisionnementRepository.findByCarburantId(carburantId);
    }
}