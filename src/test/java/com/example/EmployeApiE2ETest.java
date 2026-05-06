package com.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Pourquoi Testcontainers est préférable à H2 pour tester la persistance ?
 * Testcontainers permet d'utiliser la même base de données qu'en production (ici PostgreSQL), 
 * garantissant que les fonctionnalités spécifiques (types de données, fonctions SQL, contraintes) 
 * se comportent de la même manière. H2 peut parfois masquer des bugs liés à des différences de dialecte SQL.
 * H2 reste approprié pour des tests unitaires rapides de la couche repository où la fidélité totale 
 * n'est pas critique, ou quand Docker n'est pas disponible.
 */
class EmployeApiE2ETest extends AbstractIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @DisplayName("Scénario E2E : Créer puis récupérer un employé")
    void devrais_creer_et_recuperer_employe() {
        // 1. POST /api/employes
        EmployeDTO dto = new EmployeDTO("Jean", "RH", 3000);
        ResponseEntity<Employe> responsePost = restTemplate.postForEntity("/api/employes", dto, Employe.class);

        assertThat(responsePost.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responsePost.getBody()).isNotNull();
        Long id = responsePost.getBody().getId();

        // 2. GET /api/employes/{id}
        ResponseEntity<Employe> responseGet = restTemplate.getForEntity("/api/employes/" + id, Employe.class);
        assertThat(responseGet.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseGet.getBody().getNom()).isEqualTo("Jean");
    }

    @Test
    @DisplayName("Scénario E2E : Suppression d'un employé")
    void devrais_supprimer_employe() {
        // 1. Créer
        EmployeDTO dto = new EmployeDTO("ASupprimer", "IT", 2000);
        ResponseEntity<Employe> responsePost = restTemplate.postForEntity("/api/employes", dto, Employe.class);
        Long id = responsePost.getBody().getId();

        // 2. DELETE
        restTemplate.delete("/api/employes/" + id);

        // 3. GET -> 404
        ResponseEntity<String> responseGet = restTemplate.getForEntity("/api/employes/" + id, String.class);
        assertThat(responseGet.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
