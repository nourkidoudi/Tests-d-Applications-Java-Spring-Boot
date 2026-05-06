package com.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeController.class)
class EmployeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("GET /api/employes → 200 OK avec liste")
    void listerTous_devrait_retourner_liste_employes() throws Exception {
        when(service.listerTous()).thenReturn(Arrays.asList(
                new Employe("Salah", "IT", 3500),
                new Employe("Amar", "IT", 4200)
        ));

        mockMvc.perform(get("/api/employes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nom").value("Salah"))
                .andExpect(jsonPath("$[1].nom").value("Amar"));
    }

    @Test
    @DisplayName("GET /api/employes/99 → 404 Not Found")
    void trouverParId_inexistant_devrait_retourner_404() throws Exception {
        when(service.trouverParId(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/employes/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST /api/employes avec corps invalide → 400 Bad Request")
    void creer_invalide_devrait_retourner_400() throws Exception {
        String invalidJson = "{\"nom\": \"\", \"salaire\": -500}";

        mockMvc.perform(post("/api/employes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidJson))
                .andExpect(status().isBadRequest());

        verify(service, never()).creer(any());
    }
}
