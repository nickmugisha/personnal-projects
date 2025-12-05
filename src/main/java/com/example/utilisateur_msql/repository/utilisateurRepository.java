package com.example.utilisateur_msql.repository;

import com.example.utilisateur_msql.model.utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface utilisateurRepository extends JpaRepository<utilisateur, Long> {
}
