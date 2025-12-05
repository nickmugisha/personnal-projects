// src/main/java/com/university/fuel/service/CarburantService.java
package com.university.fuel.service;

import com.university.fuel.entity.Carburant;
import com.university.fuel.repository.CarburantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CarburantService {

    @Autowired
    private CarburantRepository carburantRepository;

    public Carburant createCarburant(Carburant carburant) {
        if (carburantRepository.existsByType(carburant.getType())) {
            throw new RuntimeException("Carburant type already exists");
        }
        return carburantRepository.save(carburant);
    }

    public List<Carburant> getAllCarburants() {
        return carburantRepository.findAll();
    }

    public Optional<Carburant> getCarburantById(Long id) {
        return carburantRepository.findById(id);
    }

    public Carburant updateCarburant(Long id, Carburant carburantDetails) {
        Carburant carburant = carburantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Carburant not found"));
        
        carburant.setType(carburantDetails.getType());
        carburant.setDescription(carburantDetails.getDescription());
        
        return carburantRepository.save(carburant);
    }

    public void deleteCarburant(Long id) {
        if (!carburantRepository.existsById(id)) {
            throw new RuntimeException("Carburant not found");
        }
        carburantRepository.deleteById(id);
    }
}