// src/main/java/com/university/fuel/repository/CarburantRepository.java
package com.university.fuel.repository;

import com.university.fuel.entity.Carburant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CarburantRepository extends JpaRepository<Carburant, Long> {
    Optional<Carburant> findByType(String type);
    Boolean existsByType(String type);
}