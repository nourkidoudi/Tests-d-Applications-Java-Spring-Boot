package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ReservationServiceTest {

    ReservationService service;

    @BeforeEach
    void setUp() {
        service = new ReservationService();
    }

    @Test
    @DisplayName("Séjour de 3 nuits sans remise applique le prix plein")
    void calculerPrixTotal_sejour_court_applique_prix_plein() {
        assertThat(service.calculerPrixTotal(100, 3)).isEqualTo(300);
    }

    @Test
    @DisplayName("Séjour de 7 nuits applique une remise de 10 %")
    void calculerPrixTotal_sejour_long_applique_remise() {
        assertThat(service.calculerPrixTotal(100, 7)).isEqualTo(630);
    }

    @Test
    @DisplayName("Prix négatif provoque IllegalArgumentException")
    void calculerPrixTotal_prix_negatif_lance_exception() {
        assertThatThrownBy(() -> service.calculerPrixTotal(-100, 3))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Vérifie la disponibilité pour une période valide")
    void verifierDisponibilite_periode_valide_renvoie_vrai() {
        assertThat(service.verifierDisponibilite(
                LocalDate.now(),
                LocalDate.now().plusDays(2)
        )).isTrue();
    }

    @Test
    @DisplayName("Dates nulles provoquent IllegalArgumentException")
    void verifierDisponibilite_dates_nulles_lancent_exception() {
        assertThatThrownBy(() -> service.verifierDisponibilite(null, null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Le code de confirmation commence par HOTEL- et fait 12 caractères")
    void genererCodeConfirmation_format_valide() {
        String code = service.genererCodeConfirmation();
        assertThat(code).startsWith("HOTEL-").hasSize(12);
    }
}