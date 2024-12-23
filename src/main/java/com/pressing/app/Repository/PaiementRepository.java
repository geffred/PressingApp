package com.pressing.app.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pressing.app.Entity.Paiement;

public interface PaiementRepository extends JpaRepository<Paiement, Long> {

}
