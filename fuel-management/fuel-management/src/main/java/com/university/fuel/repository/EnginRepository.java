// src/main/java/com/university/fuel/repository/EnginRepository.java
package com.university.fuel.repository;

import com.university.fuel.entity.Engin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EnginRepository extends JpaRepository<Engin, Long> {
    List<Engin> findByTypeCarburantId(Long carburantId);
    Boolean existsByPlaque(String plaque);
}