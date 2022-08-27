package com.onee.gestionportefeuilles;

import com.onee.gestionportefeuilles.dao.ProjetRepository;
import com.onee.gestionportefeuilles.dao.RessourceRepository;
import com.onee.gestionportefeuilles.dao.RoleRepository;
import com.onee.gestionportefeuilles.entities.Projet;
import com.onee.gestionportefeuilles.entities.Ressource;
import com.onee.gestionportefeuilles.entities.Role;
import com.onee.gestionportefeuilles.service.ComptesService;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.transaction.Transactional;
import java.nio.file.Paths;
import java.util.ArrayList;

@SpringBootApplication
public class GestionPortefeuillesApplication implements CommandLineRunner {
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Autowired
    private ComptesService comptesService;
    //implements CommandLineRunner nous oblige de redéfinir la méthode run
    public static void main(String[] args) {
        SpringApplication.run(GestionPortefeuillesApplication.class, args);
    }

    @Autowired
    private ProjetRepository projetRepository;
    @Transactional
    void test()
    {
        Projet projet= new Projet();
        projet.setCodeProjet("testRun");
        ArrayList<Ressource> intervenants=new ArrayList<>();
        Ressource r1=comptesService.findUserByEmail("developpeur@gmail.com");
        r1.addProjetAffecte(projet);
        intervenants.add(r1);
        Ressource r2=comptesService.findUserByEmail("chefProjet@gmail.com");
//        intervenants.add(r2);
//        r2.getProjetsAffectes().add(projet);
//        projet.setIntervenants(intervenants);
        projetRepository.save(projet);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("********************************************************"+Paths.get(System.getProperty("user.home")));
        if(comptesService.findRole("Administrateur")==null)
            comptesService.saveRole(new Role(null,"Administrateur"));
        if(comptesService.findRole("Gestionnaire de portefeuilles")==null)
            comptesService.saveRole(new Role(null,"Gestionnaire de portefeuilles"));
        if(comptesService.findRole("Chef de projets")==null)
            comptesService.saveRole(new Role(null,"Chef de projets"));
        if(comptesService.findRole("Utilisateur")==null)
            comptesService.saveRole(new Role(null,"Utilisateur"));
        if(comptesService.findUserByEmail("administrateur@gmail.com")==null) {
            comptesService.saveUser(new Ressource(null, null, null, null,null,
                    "administrateur@gmail.com", "null", "123", null, null, null, null, null));
            comptesService.addRoleToUser("administrateur@gmail.com", "Administrateur");
        }
        if(comptesService.findUserByEmail("gestionnairePortefeuille@gmail.com")==null) {
            comptesService.saveUser(new Ressource(null, null, null, null, null,
                    "gestionnairePortefeuille@gmail.com", "null", "123", null, null, null, null, null));
            comptesService.addRoleToUser("gestionnairePortefeuille@gmail.com", "Gestionnaire de portefeuilles");
        }
        if(comptesService.findUserByEmail("chefProjet@gmail.com")==null) {
            comptesService.saveUser(new Ressource(null, null, null, null, null,
                    "chefProjet@gmail.com", "null", "123", null, null, null, null, null));
            comptesService.addRoleToUser("chefProjet@gmail.com", "Chef de projets");
        }
        if(comptesService.findUserByEmail("developpeur@gmail.com")==null) {
            comptesService.saveUser(new Ressource(null, null, null, null, null,
                    "developpeur@gmail.com", "null", "123", null, null, null, null, null));
            comptesService.addRoleToUser("developpeur@gmail.com", "Utilisateur");

        }
//        test();
//        //création des rôles
//        if(roleRepository.findByNomRole("Administrateur").isEmpty()) {
//            Role role = new Role();
//            role.setNomRole("Administrateur");
//            roleRepository.save(role);
//        }
//        if(roleRepository.findByNomRole("Gestionnaire de portefeuilles").isEmpty()) {
//            Role role2 = new Role();
//            role2.setNomRole("Gestionnaire de portefeuilles");
//            roleRepository.save(role2);
//        }
//        if(roleRepository.findByNomRole("Chef de projets").isEmpty()) {
//            Role role3 = new Role();
//            role3.setNomRole("Chef de projets");
//            roleRepository.save(role3);
//        }
//        if(roleRepository.findByNomRole("Intervenant développeur").isEmpty()) {
//            Role role4 = new Role();
//            roleRepository.save(role4);
//            role4.setNomRole("Intervenant développeur");
//        }
//
//
//        //création de comptes d'essais
//        if(ressourceRepository.findByEmail("administrateur@gmail.com").isEmpty()) {
//            Ressource ressource = new Ressource();
//            ressource.setEmail("administrateur@gmail.com");
//            ressource.setRoles(
//                    roleRepository.findByNomRole("Administrateur")
//            );
//            ressource.setPassword("123");
//            ressourceRepository.save(ressource);
//        }
//        if(ressourceRepository.findByEmail("gestionnairePortefeuille@gmail.com").isEmpty()) {
//            Ressource ressource = new Ressource();
//            ressource.setEmail("gestionnairePortefeuille@gmail.com");
//            ressource.setRoles(
//                    roleRepository.findByNomRole("Gestionnaire de portefeuilles")
//            );
//            ressource.setPassword("123");
//            ressourceRepository.save(ressource);
//        }
//        if(ressourceRepository.findByEmail("chefProjet@gmail.com").isEmpty()) {
//            Ressource ressource = new Ressource();
//            ressource.setEmail("chefProjet@gmail.com");
//            ressource.setRoles(
//                    roleRepository.findByNomRole("Chef de projets")
//            );
//            ressource.setPassword("123");
//            ressourceRepository.save(ressource);
//        }
//        if(ressourceRepository.findByEmail("developpeur@gmail.com").isEmpty()) {
//            Ressource ressource = new Ressource();
//            ressource.setEmail("developpeur@gmail.com");
//            ressource.setPassword("123");
//            ressource.setRoles(
//                    roleRepository.findByNomRole("Intervenant développeur")
//            );
//            ressourceRepository.save(ressource);
//        }
    }
}
