package com.onee.gestionportefeuilles.web;

import com.onee.gestionportefeuilles.dao.ActiviteRepository;
import com.onee.gestionportefeuilles.dao.RessourceRepository;
import com.onee.gestionportefeuilles.dao.RoleRepository;
import com.onee.gestionportefeuilles.entities.*;
import com.onee.gestionportefeuilles.service.ChangePasswordForm;
import com.onee.gestionportefeuilles.service.ComptesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@CrossOrigin("*")
@RestController
public class RessourcesRestController {
    @Autowired
    RessourceRepository ressourceRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    ComptesService comptesService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private ActiviteRepository activiteRepository;
    @GetMapping(path="/toutesLesRessources")
    public List<Ressource> toutesLesRessources()
    {
        return ressourceRepository.findAll();
    }
    @GetMapping(path="/ressource-par-code/{id}")
    public Ressource getRessource(@PathVariable("id") Long id)
    {
        return ressourceRepository.findById(id).get();
    }
    @PostMapping(path="/nouvelle-ressource")
    public Ressource nouvelleRessource(@RequestBody Ressource ressource)
    {
        return comptesService.saveUser(ressource);
    }
    @PutMapping(path="update-ressource/{id}")
    public Ressource updateRessource(@PathVariable("id") Long id,@RequestBody Ressource ressource)
    {
        Ressource r=ressourceRepository.findById(id).get();
        r.setEmail(ressource.getEmail());
        r.setNom(ressource.getNom());
        r.setPrenom(ressource.getPrenom());
        r.setTel(ressource.getTel());
        r.setEmploi(ressource.getEmploi());
        r.setRoles(ressource.getRoles());
        return ressourceRepository.save(r);
    }
    @GetMapping("tousLesChef")
    public List<Ressource> tousLesChef()
    {
        Role role=comptesService.findRole("Chef de projets");
        List<Ressource> ressources=ressourceRepository.findAll();
        List<Ressource> chefsdeProjet=ressourceRepository.findAll();
        ressources.forEach(c->
        {
            if(!c.getRoles().contains(role))
                chefsdeProjet.remove(c);
        });
        return chefsdeProjet;
    }
    @GetMapping("ressource-par-email/{email}")
    public Ressource ressourceParEmail(@PathVariable("email") String email )
    {
        return comptesService.findUserByEmail(email);
    }
    @PutMapping("update-ressource-sans-roles/{id}")
    public Ressource updateRessourceSansRoles(@PathVariable("id") Long id,@RequestBody Ressource ressource)
    {
        Ressource r=ressourceRepository.findById(id).get();
        r.setEmail(ressource.getEmail());
        r.setNom(ressource.getNom());
        r.setPrenom(ressource.getPrenom());
        r.setTel(ressource.getTel());
        r.setEmploi(ressource.getEmploi());
        return ressourceRepository.save(r);
    }
    @PostMapping(path = "/upload-user-photo/{id}")
    public void uploadPhoto(MultipartFile file, @PathVariable Long id) throws Exception{
        Ressource r=ressourceRepository.findById(id).get();
        String filename=file.getOriginalFilename();
        r.setNomPhoto(id+"."+filename.split("\\.")[1]);
        Files.write(Paths.get(System.getProperty("user.home")+
                "\\Documents\\Stage Portefeuilles de Projets\\imagesProjet\\Photos\\"
                +r.getNomPhoto()),file.getBytes());

        ressourceRepository.save(r);
    }
    @GetMapping(path="/photo-user/{code}",produces=
            MediaType.IMAGE_PNG_VALUE)
    public byte[] getLogo(@PathVariable("code") Long code) throws Exception{
        Ressource r=ressourceRepository.findById(code).get();
        if(r.getNomPhoto()!=null)
            return Files.readAllBytes(Paths.get(System.getProperty("user.home")+
                "\\Documents\\Stage Portefeuilles de Projets\\imagesProjet\\Photos\\"+r.getNomPhoto()));
        else
            return Files.readAllBytes(Paths.get(System.getProperty("user.home")+
                    "\\Documents\\Stage Portefeuilles de Projets\\imagesProjet\\Photos\\"+"default.png"));
    }
    @PostMapping("change_password/{code}")
    public boolean changePassword(@PathVariable("code") Long code,@RequestBody ChangePasswordForm form)
    {
        Ressource r=ressourceRepository.findById(code).get();
        String pass=bCryptPasswordEncoder.encode(form.getAncien_password());
        if(bCryptPasswordEncoder.matches(form.getAncien_password(), r.getPassword()))
        {
            r.setPassword(bCryptPasswordEncoder.encode(form.getNouveau_password()));
            ressourceRepository.save(r);
            return true;
        }else{
            return false;
        }
    }
    @PostMapping(path = "/add-activites/{id}")
    public void addActivites(@RequestBody Collection<Activite> activites, @PathVariable Long id
    ) throws Exception{
        Ressource intervenenat=ressourceRepository.findById(id).get();
        activites.forEach(activite->
        {
            activite.setIntervenant(intervenenat);
            activiteRepository.save(activite);
        });
    }
    @GetMapping(path="activitesByUser/{id}")
    public List<Activite> getActivites(@PathVariable Long id)
    {
       List<Activite> allActivites=activiteRepository.findAllByOrderByDateAsc();
       List<Activite> activites=new ArrayList<>();
       allActivites.forEach(a->{
           if(a.getIntervenant().getCodeRessource()==id)
               activites.add(a);
       });
       return activites;
    }
    @DeleteMapping(path="delete-activites/{id}")
    public void deleteAllActivites(@PathVariable Long id)
    {
        List<Activite> activites=getActivites(id);
        activites.forEach(
                activite -> {
                    activiteRepository.delete(activite);
                }
        );
    }
}