package com.example;

import java.time.LocalDate;

public class ReservationService {

    public double calculerPrixTotal(double prixParNuit, int nombreNuits) {
        if (prixParNuit < 0) {
            throw new IllegalArgumentException();
        }

        double total = prixParNuit * nombreNuits;

        if (nombreNuits >= 7) {
            total *= 0.9;
        }

        return total;
    }

    public boolean verifierDisponibilite(LocalDate debut, LocalDate fin) {
        if (debut == null || fin == null) {
            throw new IllegalArgumentException();
        }
        return debut.isBefore(fin);
    }

    public String genererCodeConfirmation() {
        int random = (int)(Math.random() * 900000) + 100000;
        return "HOTEL-" + random;
    }
}