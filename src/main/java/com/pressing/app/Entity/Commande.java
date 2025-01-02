package com.pressing.app.Entity;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "commandes")
public class Commande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull(message = "Le champ date de commande ne peut pas être vide")
    @FutureOrPresent(message = "La date de commande doit être dans le futur ou aujourd'hui")
    @Column(name = "dateCommande")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateCommande;

    @FutureOrPresent(message = "La date de livraison doit être dans le futur ou aujourd'hui")
    @NotNull(message = "La date de livraison ne peut pas être vide")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "dateLivraison")
    private LocalDate dateLivraison;

    @Column(name = "payee")
    private boolean payee;

    @NotNull(message = "Le champ Client ne peut pas être vide")
    @ManyToOne(fetch = FetchType.LAZY)
    private Client client;

    @NotNull(message = "Le champ employé ne peut pas être vide")
    @ManyToOne(fetch = FetchType.LAZY)
    private Employe employe;

    @NotNull(message = "Le champ vêtement ne peut pas être vide")
    @ManyToOne(fetch = FetchType.LAZY)
    private Vetement vetement;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paiement_id")
    private Paiement paiement;

    public Commande() {
    }

    public Commande(LocalDate dateCommande, LocalDate dateLivraison, boolean payee) {
        this.dateCommande = dateCommande;
        this.dateLivraison = dateLivraison;
        this.payee = payee;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(LocalDate dateCommande) {
        this.dateCommande = dateCommande;
    }

    public LocalDate getDateLivraison() {
        return dateLivraison;
    }

    public void setDateLivraison(LocalDate dateLivraison) {
        this.dateLivraison = dateLivraison;
    }

    public boolean isPayee() {
        return payee;
    }

    public void setPayee(boolean payee) {
        this.payee = payee;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Employe getEmploye() {
        return employe;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }

    public Paiement getPaiement() {
        return paiement;
    }

    public void setPaiement(Paiement paiement) {
        this.paiement = paiement;
    }

    public Vetement getVetement() {
        return vetement;
    }

    public void setVetement(Vetement vetement) {
        this.vetement = vetement;
    }
}
