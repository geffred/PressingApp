package com.pressing.app.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pressing.app.Entity.Commande;

public interface CommandeRepository extends JpaRepository<Commande, Long> {
    List<Commande> findByClientNomContaining(String nom);

    List<Commande> findByPayee(Boolean payee);
}
