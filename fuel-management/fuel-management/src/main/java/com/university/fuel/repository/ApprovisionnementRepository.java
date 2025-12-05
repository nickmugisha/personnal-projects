// src/main/java/com/university/fuel/repository/ApprovisionnementRepository.java
package com.university.fuel.repository;

import com.university.fuel.entity.Approvisionnement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ApprovisionnementRepository extends JpaRepository<Approvisionnement, Long> {
    List<Approvisionnement> findByCarburantId(Long carburantId);
}