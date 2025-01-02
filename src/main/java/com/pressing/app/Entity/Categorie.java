package com.pressing.app.Entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "categories")
public class Categorie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nom", unique = true)
    @NotNull(message = "Le nom ne peut pas être null")
    @NotEmpty(message = "Le nom ne peut pas être vide")
    private String nom;

    @NotNull(message = "Le prix unitaire ne peut pas être null")
    @Min(value = 0, message = "Le prix unitaire doit être positif")
    @Column(name = "prixUnitaire")
    private double prixUnitaire;

    @NotEmpty(message = "La description ne peut pas être vide")
    @NotNull(message = "La description ne peut pas être null")
    @Column(name = "description")
    private String description;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "categorie_id", updatable = false)
    private List<Vetement> vetements;

    public Categorie() {
    }

    public Categorie(String nom, double prixUnitaire, String description) {
        this.nom = nom;
        this.prixUnitaire = prixUnitaire;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public List<Vetement> getVetements() {
        return vetements;
    }

    public void setVetements(List<Vetement> vetements) {
        this.vetements = vetements;
    }

}
