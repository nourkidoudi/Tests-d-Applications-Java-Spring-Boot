package com.example;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class EmployeDTO {
    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    private String departement;

    @Min(value = 1, message = "Le salaire doit être supérieur à 0")
    private double salaire;

    public EmployeDTO() {}

    public EmployeDTO(String nom, String departement, double salaire) {
        this.nom = nom;
        this.departement = departement;
        this.salaire = salaire;
    }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getDepartement() { return departement; }
    public void setDepartement(String departement) { this.departement = departement; }
    public double getSalaire() { return salaire; }
    public void setSalaire(double salaire) { this.salaire = salaire; }
}
