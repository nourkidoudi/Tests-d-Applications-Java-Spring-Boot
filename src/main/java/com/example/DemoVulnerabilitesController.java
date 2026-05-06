package com.example;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DemoVulnerabilitesController {

    @Autowired
    private EntityManager em;

    // Faille 1 : injection SQL via concaténation directe
    @GetMapping("/search")
    public List rechercher(@RequestParam String nom) {
        // VULNÉRABLE
        return em.createQuery("SELECT e FROM Employe e WHERE e.nom = '" + nom + "'").getResultList();
    }

    // Faille 2 : secret en dur dans le code
    // VULNÉRABLE
    private static final String API_SECRET = "MonSecretSuperConfidentiel123!";
}
