package com.onee.gestionportefeuilles.dao;

import com.onee.gestionportefeuilles.entities.Ressource;
import com.onee.gestionportefeuilles.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
@CrossOrigin("*")
public interface RessourceRepository extends JpaRepository<Ressource,Long> {
    List<Ressource> findByEmail(String email);
}
