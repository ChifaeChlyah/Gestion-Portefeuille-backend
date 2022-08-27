package com.onee.gestionportefeuilles.web;

import com.onee.gestionportefeuilles.dao.FamilleProjetRepository;
import com.onee.gestionportefeuilles.entities.FamilleProjet;
import com.onee.gestionportefeuilles.entities.Projet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin("*")
@RestController
public class familleProjetRestController {
    @Autowired
    FamilleProjetRepository familleProjetRepository;
    @GetMapping(path="/tousLesPortefeuilles")
    public List<FamilleProjet> tousLesProjets()
    {
        return familleProjetRepository.findAll();
    }
    @PostMapping(path="/add-portefeuille")
    public boolean addPortfeuille(@RequestBody FamilleProjet portefeuille)
    {
        if(familleProjetRepository.findById(portefeuille.getCodeFamille()).isPresent())
            return false;
        familleProjetRepository.save(portefeuille);
        return true;
    }

}
