package com.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FacturationServiceTest {

    @Mock
    ClientRepository clientRepository;

    @Mock
    NotificationService notificationService;

    @InjectMocks
    FacturationService facturationService;

    @Test
    @DisplayName("Facture nominale est créée et la notification est envoyée")
    void genererFacture_client_existant_envoie_notification_et_retourne_facture() {
        Client client = new Client(1L, "contact@acme.com");
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));

        Facture facture = facturationService.genererFacture(1L, 250.0);

        assertThat(facture).isNotNull();
        assertThat(facture.getMontant()).isEqualTo(250.0);
        assertThat(facture.getClient()).isEqualTo(client);

        verify(clientRepository).findById(1L);
        verify(notificationService).envoyerEmail(eq(client), eq("Votre facture : 250.0 €"));
    }

    @Test
    @DisplayName("Client introuvable lève ClientNotFoundException et n'envoie pas de notification")
    void genererFacture_client_inexistant_lance_exception_et_ne_notifie_pas() {
        when(clientRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> facturationService.genererFacture(99L, 250.0))
                .isInstanceOf(ClientNotFoundException.class)
                .hasMessageContaining("Client introuvable");

        verify(clientRepository).findById(99L);
        verify(notificationService, never()).envoyerEmail(any(), any(String.class));
    }
}
