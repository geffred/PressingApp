package com.pressing.app.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pressing.app.Entity.Vetement;

public interface VetementRepository extends JpaRepository<Vetement, Long> {

}
