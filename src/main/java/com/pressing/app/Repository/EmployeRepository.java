package com.pressing.app.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pressing.app.Entity.Employe;

public interface EmployeRepository extends JpaRepository<Employe, Long> {

}
