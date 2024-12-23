package com.pressing.app.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pressing.app.Entity.Commande;

public interface CommandeRepository extends JpaRepository<Commande, Long> {

}
