package com.onee.gestionportefeuilles.web;

import com.onee.gestionportefeuilles.dao.FamilleProjetRepository;
import com.onee.gestionportefeuilles.dao.RessourceRepository;
import com.onee.gestionportefeuilles.entities.FamilleProjet;
import com.onee.gestionportefeuilles.entities.Ressource;
import com.onee.gestionportefeuilles.service.ComptesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
public class RessourcesRestController {
    @Autowired
    RessourceRepository ressourceRepository;
    @Autowired
    ComptesService comptesService;
    @GetMapping(path="/toutesLesRessources")
    public List<Ressource> tousLesProjets()
    {
        return ressourceRepository.findAll();
    }
    @GetMapping(path="/toutesLesRessources/{id}")
    public Ressource tousLesProjets(@PathVariable("id") Long id)
    {
        return ressourceRepository.findById(id).get();
    }
    @PostMapping(path="/nouvelle-ressource")
    public Ressource nouvelleRoussource(@RequestBody Ressource ressource)
    {
        return comptesService.saveUser(ressource);
    }
}