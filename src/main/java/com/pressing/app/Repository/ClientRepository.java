package com.pressing.app.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pressing.app.Entity.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {

}
