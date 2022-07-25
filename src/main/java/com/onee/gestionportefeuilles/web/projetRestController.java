package com.onee.gestionportefeuilles.web;

import com.onee.gestionportefeuilles.dao.ProjetRepository;
import com.onee.gestionportefeuilles.entities.Projet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@CrossOrigin("*")
@RestController
public class projetRestController {

    ProjetRepository projetRepository;
    public projetRestController(ProjetRepository projetRepository)
    {
        this.projetRepository=projetRepository;
    }
    @GetMapping(path="/logoProjet/{id}",produces=
            MediaType.IMAGE_PNG_VALUE)
    public byte[] getLogo(@PathVariable("id") String id) throws Exception{
        Projet p=projetRepository.findById(id).get();
        return Files.readAllBytes(Paths.get(System.getProperty("user.home")+
                "\\Documents\\Stage Portefeuilles de Projets\\imagesProjet\\Logos\\"+p.getLogo()));
    }
    @GetMapping(path="/tousLesProjets")
    public List<Projet> tousLesProjets()
    {
        return projetRepository.findAll();
    }
}
