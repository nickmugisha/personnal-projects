// src/main/java/com/university/fuel/repository/StockCarburantRepository.java
package com.university.fuel.repository;

import com.university.fuel.entity.StockCarburant;
import com.university.fuel.entity.Carburant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface StockCarburantRepository extends JpaRepository<StockCarburant, Long> {
    Optional<StockCarburant> findByCarburant(Carburant carburant);
    Optional<StockCarburant> findByCarburantId(Long carburantId);
}