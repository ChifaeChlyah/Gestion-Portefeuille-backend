package com.onee.gestionportefeuilles.dao;

import com.onee.gestionportefeuilles.entities.Risque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin("*")
@RepositoryRestResource
public interface RisqueRepository extends JpaRepository<Risque,Long> {
}
