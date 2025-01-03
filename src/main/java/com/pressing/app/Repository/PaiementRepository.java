package com.pressing.app.Repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pressing.app.Entity.Paiement;

public interface PaiementRepository extends JpaRepository<Paiement, Long> {
    List<Paiement> findByCommandeClientNomContaining(String nom);

    List<Paiement> findByCommandePayee(Boolean payee);

    List<Paiement> findByDatePaiementBetween(LocalDate dateDebut, LocalDate dateFin);

}
