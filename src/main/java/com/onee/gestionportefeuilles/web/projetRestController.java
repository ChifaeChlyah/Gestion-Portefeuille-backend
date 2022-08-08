package com.onee.gestionportefeuilles.web;

import com.onee.gestionportefeuilles.dao.*;
import com.onee.gestionportefeuilles.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin("*")
@RestController
public class projetRestController {
    @Autowired
    ProjetRepository projetRepository;
    @Autowired
    RessourceRepository ressourceRepository;
    @Autowired
    PieceJointeRepository pieceJointeRepository;
    @Autowired
    RisqueRepository risqueRepository;
    @Autowired
    TacheRepository tacheRepository;
    @Autowired
    InterventionRepository interventionRepository;
    public projetRestController(ProjetRepository projetRepository)
    {
        this.projetRepository=projetRepository;
    }
    @GetMapping(path="/logoProjet/{id}",produces=
            MediaType.IMAGE_PNG_VALUE)
    public byte[] getLogo(@PathVariable("id") String id) throws Exception{
        Projet p=projetRepository.findById(id).get();
        if(p.getLogo()==null)
            return Files.readAllBytes(Paths.get(System.getProperty("user.home")+
                    "\\Documents\\Stage Portefeuilles de Projets\\imagesProjet\\Logos\\"+"default-logo.png"));
        else
            return Files.readAllBytes(Paths.get(System.getProperty("user.home")+
                "\\Documents\\Stage Portefeuilles de Projets\\imagesProjet\\Logos\\"+p.getLogo()));
    }
    @PostMapping(path = "/upload-logo/{id}")
    public void uploadLogo(MultipartFile file, @PathVariable String id) throws Exception{
        Projet p=projetRepository.findById(id).get();
        String filename=file.getOriginalFilename();
        p.setLogo(id+"."+filename.split("\\.")[1]);
        Files.write(Paths.get(System.getProperty("user.home")+
                "\\Documents\\Stage Portefeuilles de Projets\\imagesProjet\\Logos\\"
                +p.getLogo()),file.getBytes());

        projetRepository.save(p);
    }
    @PostMapping(path = "/upload-piece-jointe/{id}")
    public void uploadPieceJointe(@RequestPart("desc") String desc,
                                  @RequestPart("file")  MultipartFile file,
                                  @PathVariable String id
    ) throws Exception{
        Projet p=projetRepository.findById(id).get();
        String filename=file.getOriginalFilename();
        PieceJointe pj=new PieceJointe();
        pj.setNom(id+"-"+filename);
        pj.setDescription(desc);
        Files.write(Paths.get(System.getProperty("user.home")+
                "\\Documents\\Stage Portefeuilles de Projets\\piecesJointes\\"
                +pj.getNom()),file.getBytes());
        pj.setProjet(p);
        pieceJointeRepository.save(pj);

    }
    @PostMapping(path = "/add-risque/{id}")
    public void addRisque(@RequestBody Risque risque,@PathVariable String id
    ) throws Exception{
        Projet p=projetRepository.findById(id).get();
        risque.setProjet(p);
        risqueRepository.save(risque);
    }
    @PostMapping(path = "/add-tache/{id}")
    public void addTache(@RequestBody Collection<Tache> taches, @PathVariable String id
    ) throws Exception{
        Projet p=projetRepository.findById(id).get();
        Map<Long, Long> map = new HashMap<>();
        taches.forEach(tache->
       {
            tache.setProjet(p);
            Long indice=tache.getIdTache();
            tache.setIdTache(null);
            if(tache.getTacheMere()!=null)
                tache.getTacheMere().setIdTache(map.get(tache.getTacheMere().getIdTache()));
            if(tache.getDependances()!=null) {
                tache.getDependances().forEach(dep -> {
                    dep.setIdTache(map.get(dep.getIdTache()));
                });
            }
            tacheRepository.save(tache);
            map.put(indice,tache.getIdTache());
            tache.getInterventions().forEach(i->{
                i.setTache(tache);
                interventionRepository.save(i);
            });
       });
    }
    @GetMapping(path="/tousLesProjets")
    public List<Projet> tousLesProjets()
    {
        return projetRepository.findAll();
    }
    @PostMapping(path="/add-projet")
    public Projet addProjet(@RequestBody Projet projet){
        System.out.println("*********************************************"+projet.getIntervenants());
        Projet p=projetRepository.save(projet);
//        for (Ressource r:projet.getIntervenants()) {
//            r.addProjetAffecte(projet);
//            ressourceRepository.save(r);
//        }
        return p;
    }

}
