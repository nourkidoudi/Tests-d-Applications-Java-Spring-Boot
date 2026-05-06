package com.example;

public class Facture {

    private final Client client;
    private final double montant;

    public Facture(Client client, double montant) {
        this.client = client;
        this.montant = montant;
    }

    public Client getClient() {
        return client;
    }

    public double getMontant() {
        return montant;
    }
}
