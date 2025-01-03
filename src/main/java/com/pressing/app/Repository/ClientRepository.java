package com.pressing.app.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pressing.app.Entity.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
    List<Client> findByNomContainingOrEmailContaining(String nom, String email);

}
