// src/main/java/com/university/fuel/service/EnginService.java
package com.university.fuel.service;

import com.university.fuel.entity.Engin;
import com.university.fuel.repository.EnginRepository;
import com.university.fuel.repository.CarburantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class EnginService {

    @Autowired
    private EnginRepository enginRepository;

    @Autowired
    private CarburantRepository carburantRepository;

    public Engin createEngin(Engin engin) {
        if (enginRepository.existsByPlaque(engin.getPlaque())) {
            throw new RuntimeException("Engin with this plaque already exists");
        }
        
        // Verify carburant exists
        carburantRepository.findById(engin.getTypeCarburant().getId())
                .orElseThrow(() -> new RuntimeException("Carburant type not found"));
        
        return enginRepository.save(engin);
    }

    public List<Engin> getAllEngins() {
        return enginRepository.findAll();
    }

    public Optional<Engin> getEnginById(Long id) {
        return enginRepository.findById(id);
    }

    public Engin updateEngin(Long id, Engin enginDetails) {
        Engin engin = enginRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Engin not found"));
        
        engin.setType(enginDetails.getType());
        engin.setPlaque(enginDetails.getPlaque());
        engin.setConsommationMoyenne(enginDetails.getConsommationMoyenne());
        engin.setTypeCarburant(enginDetails.getTypeCarburant());
        
        return enginRepository.save(engin);
    }

    public void deleteEngin(Long id) {
        if (!enginRepository.existsById(id)) {
            throw new RuntimeException("Engin not found");
        }
        enginRepository.deleteById(id);
    }
}