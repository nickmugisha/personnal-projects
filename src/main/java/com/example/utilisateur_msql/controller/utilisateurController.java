package com.example.utilisateur_msql.controller;

import com.example.utilisateur_msql.model.utilisateur;
import com.example.utilisateur_msql.repository.utilisateurRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/utilisateurs")
public class utilisateurController {

    private final utilisateurRepository repository;

    public utilisateurController(utilisateurRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public utilisateur create(@RequestBody utilisateur utilisateur) {
        return repository.save(utilisateur);
    }

    @GetMapping
    public List<utilisateur> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public utilisateur getById(@PathVariable Long id) {
        return repository.findById(id).orElseThrow();
    }

    @PutMapping("/{id}")
    public utilisateur update(@PathVariable Long id, @RequestBody utilisateur updated) {
        return repository.findById(id).map(u -> {
            u.setNom(updated.getNom());
            u.setEmail(updated.getEmail());
            return repository.save(u);
        }).orElseThrow();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
