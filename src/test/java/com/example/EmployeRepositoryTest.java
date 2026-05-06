package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class EmployeRepositoryTest {

    @Autowired
    private EmployeRepository repository;

    @BeforeEach
    void setUp() {
        repository.save(new Employe("Salah", "Informatique", 3500.0));
        repository.save(new Employe("Eya", "RH", 2800.0));
        repository.save(new Employe("Amar", "Informatique", 4200.0));
    }

    @Test
    @DisplayName("Recherche par département Informatique")
    void findByDepartement_devrait_retourner_employes_du_departement() {
        List<Employe> result = repository.findByDepartement("Informatique");

        assertThat(result).hasSize(2);
        assertThat(result).extracting(Employe::getNom)
                .containsExactlyInAnyOrder("Salah", "Amar");
    }

    @Test
    @DisplayName("Recherche par salaire supérieur à un seuil")
    void findBySalaireSuperieurA_devrait_retourner_employes_bien_payes() {
        List<Employe> result = repository.findBySalaireSuperieurA(3000.0);

        assertThat(result).hasSize(2);
        assertThat(result).extracting(Employe::getNom)
                .containsExactlyInAnyOrder("Salah", "Amar");
        assertThat(result).extracting(Employe::getNom)
                .doesNotContain("Eya");
    }

    @Test
    @DisplayName("Recherche d'un département inexistant")
    void findByDepartement_inexistant_devrait_retourner_liste_vide() {
        List<Employe> result = repository.findByDepartement("Comptabilité");

        assertThat(result).isEmpty();
    }
}
