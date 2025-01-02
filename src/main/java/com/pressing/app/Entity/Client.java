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
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotEmpty(message = "Le nom ne peut pas être vide")
    @NotNull(message = "Le nom ne peut pas être null")
    @Column(name = "nom")
    private String nom;

    @NotEmpty(message = "Le prénom ne peut pas être vide")
    @NotNull(message = "Le prénom ne peut pas être null")
    @Column(name = "prenom")
    private String prenom;

    @NotEmpty(message = "L'email ne peut pas être vide")
    @NotNull(message = "L'email ne peut pas être null")
    @Column(name = "email", unique = true)
    private String email;

    @NotEmpty(message = "Le téléphone ne peut pas être vide")
    @NotNull(message = "Le téléphone ne peut pas être null")
    @Column(name = "telephone", unique = true)
    private String telephone;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id", updatable = false)
    private List<Commande> commandes;

    public Client() {
    }

    public Client(String nom, String prenom, String email, String telephone) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

}
