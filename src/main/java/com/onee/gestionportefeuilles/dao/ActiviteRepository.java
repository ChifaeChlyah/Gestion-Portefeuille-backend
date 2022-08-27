package com.onee.gestionportefeuilles.dao;

import com.onee.gestionportefeuilles.entities.Activite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin("*")
@RepositoryRestResource
public interface ActiviteRepository extends JpaRepository<Activite,Long> {
    public List<Activite> findAllByOrderByDateAsc();
}
