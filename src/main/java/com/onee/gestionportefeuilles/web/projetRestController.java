package com.onee.gestionportefeuilles.web;

import com.onee.gestionportefeuilles.dao.*;
import com.onee.gestionportefeuilles.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

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
    @Autowired
    ActiviteRepository activiteRepository;
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
        AtomicReference<Date> premiereDate=new AtomicReference<>();
        premiereDate.set(null);
        AtomicReference<Date> derniereDate=new AtomicReference<>();
        premiereDate.set(null);
        AtomicBoolean estTermine= new AtomicBoolean(true);
        AtomicReference<Double> avancement= new AtomicReference<>((double) 0);
        Projet p=projetRepository.findById(id).get();
        System.out.println(taches);
        Map<Long, Long> map = new HashMap<>();
        final double[] avancementInterventions = {0};
        taches.forEach(tache->
       {
           if(tache.getDateDebutRelle()!=null)
               tache.setDateDebutPrevue(tache.getDateDebutRelle());
           if(tache.getDateFinRelle()!=null)
               tache.setDateFinPrevue(tache.getDateFinRelle());
           avancement.updateAndGet(v -> new Double((double) (v + tache.getAvancement() / taches.size())));
           if(tache.getAvancement()>0) {
               if (premiereDate.get() == null || !premiereDate.get().before(tache.getDateDebutRelle()))
                   premiereDate.set(tache.getDateDebutRelle());
               if(tache.getAvancement()==100)
               {
               if (derniereDate.get() == null || !derniereDate.get().after(tache.getDateFinRelle()))
                   derniereDate.set(tache.getDateFinRelle());
               }
               else
                   estTermine.set(false);
           }
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
            if(tache.getInterventions()!=null)
                avancementInterventions[0] =0;
            tache.getInterventions().forEach(i->{
                if(i!=null) {
                    avancementInterventions[0] +=i.getAvancement()*i.getAffectation()/100;
                }
            });
            if(avancementInterventions[0] !=tache.getAvancement())
            {
                tache.getInterventions().forEach(i->{
                    if(i!=null) {
                        i.setTache(tache);
                        i.setAvancement(tache.getAvancement());
                        interventionRepository.save(i);
                    }
                });
            }
            else{
                tache.getInterventions().forEach(i->{
                    if(i!=null) {
                        i.setTache(tache);
                        interventionRepository.save(i);
                    }
                });
            }
       });
        p.setAvancement(avancement.get());
        if(estTermine.get())
        {
            p.setDateFinReelle(derniereDate.get());
        }
        projetRepository.save(p);
        if(premiereDate.get()!=null) {
            p.setDateDebutReelle(premiereDate.get());
            projetRepository.save(p);
        }
    }
    @DeleteMapping("delete-all-taches/{codeProjet}")
    public Projet deleteAllTaches(@PathVariable String codeProjet)
    {
        Projet projet=projetRepository.findById(codeProjet).get();
        projet.getTaches().forEach(t->
        {
            t.setTacheMere(null);
            t.setDependances(new ArrayList<>());
        });
        projet.getTaches().forEach(t->
        {
            t.getInterventions().forEach(i->
            {
                interventionRepository.delete(i);
            });
            t.getActivites().forEach(a->{
                activiteRepository.delete(a);
            });
            tacheRepository.delete(t);
        });
        return projet;
    }
    @GetMapping(path="/tousLesProjets")
    public List<Projet> tousLesProjets()
    {
        return projetRepository.findAll();
    }
    @GetMapping(path="/projet-by-code/{code}")
    public Projet tousLesProjets(@PathVariable String code )
    {
        Projet p= projetRepository.findById(code).get();
        return p;
    }
    @GetMapping(path="/projets-geres/{codeRessource}")
    public List<Projet> projetsGeres(@PathVariable Long codeRessource )
    {
        List<Projet> projets= projetRepository.findAll();
        List<Projet> projets1=new ArrayList<>();
        projets.forEach(p->{
            if(p.getChefProjet().getCodeRessource()==codeRessource)
                projets1.add(p);
        });
        return projets1;
    }
    @GetMapping(path="/projets-affectes/{codeRessource}")
    public List<Projet> projetsAffectes(@PathVariable Long codeRessource )
    {
        List<Projet> projets= projetRepository.findAll();
        List<Projet> projets1=new ArrayList<>();
        projets.forEach(p->{
            p.getIntervenants().forEach(i->{
                if(i.getCodeRessource()==codeRessource)
                    projets1.add(p);
            });
        });
        return projets1;
    }
    @GetMapping(path="/taches-affectes/{codeRessource}")
    public List<Tache> tachesAffectes(@PathVariable Long codeRessource )
    {
        List<Tache> taches=new ArrayList<>();
        interventionRepository.findAll().forEach(i->{
            if(i.getIntervenant().getCodeRessource()==codeRessource) {
                taches.add(i.getTache());
            }
        });
        return taches;
    }
    @GetMapping(path="/projet-par-tache/{idTache}")
    public Projet projetParTache(@PathVariable Long idTache)
    {
        final Projet[] projet = {null};
        List<Projet> projets=projetRepository.findAll();
        projets.forEach(p->{
            p.getTaches().forEach(t->{
                System.out.println(t.getIdTache());
                System.out.println(idTache);
                System.out.println(t.getIdTache().equals(idTache));
                if(t.getIdTache().equals(idTache))
                   projet[0] =p;
            });
        });
        return projet[0];
    }
    @PostMapping(path="/add-projet")
    public boolean addProjet(@RequestBody Projet projet){
        if(projetRepository.findById(projet.getCodeProjet()).isPresent())
            return false;
        projet.setDateDebutPrevue(projet.getDateDebutPlanifiee());
        projet.setDateFinPrevue(projet.getDateFinPlanifiee());
        List<Projet> predecesseurs=new ArrayList<>();
        if(projet.getPredecesseurs()!=null) {
            projet.getPredecesseurs().forEach(p ->
            {
                p.getPredecesseurs().forEach(
                        pre -> {
                            AtomicBoolean contains = new AtomicBoolean(false);
                            projet.getPredecesseurs().forEach(
                                    proj -> {
                                        if (proj.getCodeProjet().equals(pre.getCodeProjet()))
                                            contains.set(true);
                                    }
                            );
                            if (!contains.get())
                                predecesseurs.add(pre);
                        }
                );
            });
        }
        Projet p=projetRepository.save(projet);
        p.setTaches(new ArrayList<>());
        return true;
    }
    @PostMapping(path="/update-projet")
    public Projet updateProjet(@RequestBody Projet projet){
        List<Projet> predecesseurs=new ArrayList<>();
        if(projet.getDateDebutReelle()!=null)
            projet.setDateDebutPrevue(projet.getDateDebutReelle());
        if(projet.getDateFinReelle()!=null)
            projet.setDateFinPrevue(projet.getDateFinReelle());
        projet.getPredecesseurs().forEach(p->
        {
            p.getPredecesseurs().forEach(
                    pre->{
                        AtomicBoolean contains= new AtomicBoolean(false);
                        projet.getPredecesseurs().forEach(
                                proj->{
                                    if(proj.getCodeProjet().equals(pre.getCodeProjet()))
                                        contains.set(true);
                                }
                        );
                        if(!contains.get())
                            predecesseurs.add(pre);
                    }
            );
        });
        projet.getPredecesseurs().addAll(predecesseurs);
        Projet projet1=projetRepository.findById(projet.getCodeProjet()).get();
        projet.setTaches(projet1.getTaches());
        return projetRepository.save(projet);
    }

    @DeleteMapping("delete-risque/{id}")
    public void deletePieceJointe(@PathVariable Long id){
        Risque r=risqueRepository.findById(id).get();
        risqueRepository.delete(r);
    }
    @DeleteMapping("delete-projet/{id}")
    public void deleteProjet(@PathVariable String id){
        Projet pj=projetRepository.findById(id).get();
        projetRepository.findAll().forEach(projet -> {
            Collection<Projet> ps=new ArrayList<>();
            projet.getPredecesseurs().forEach(predecesseur->{
                if(predecesseur.getCodeProjet().equals(id)) {
                    ps.add(projet);
                }
            });
            ps.forEach(p->{
                p.getPredecesseurs().remove(pj);
                projetRepository.save(p);
            });

        });
        pj.setIntervenants(new HashSet<>());
        projetRepository.save(pj);
        risqueRepository.deleteAll(pj.getRisques());
        pieceJointeRepository.deleteAll(pj.getPieceJointes());
        deleteAllTaches(pj.getCodeProjet());
        projetRepository.deleteById(id);
    }
    @PostMapping("update-tache/{glissement}")
    public void updateTache(@RequestBody Tache tache,@PathVariable int glissement)
    {
        Date today = tache.getDateFinPrevue();
        long ltime = today.getTime()+ (long) glissement *24*60*60*1000;
        tache.setDateFinPrevue( new Date(ltime));
        if(tache.getProjet().getDateFinPrevue().before(tache.getDateFinPrevue())) {
            Projet p=tache.getProjet();
            p.setDateFinPrevue(tache.getDateFinPrevue());
            projetRepository.save(p);
        }
        tacheRepository.save(tache);
    }
    @PostMapping("update-intervention-avancement/{idTache}")
    public void updateInterventionAvancement(@RequestBody Intervention intervention,@PathVariable Long idTache)
    {
        Tache tache=tacheRepository.findById(idTache).get();
        if(intervention.getAvancement()>0&&tache.getAvancement()==0&&tache.getDateDebutRelle()==null)
        {
            tache.setDateDebutRelle(tache.getDateDebutPrevue());
            if(tache.getProjet().getDateDebutReelle()==null) {
                Projet p = tache.getProjet();
                p.setDateDebutReelle(p.getDateDebutPrevue());
                projetRepository.save(p);
            }
        }
        intervention.setTache(tache);
        interventionRepository.save(intervention);
        tache.setAvancement(0);
        tache.getInterventions().forEach(i->{
            tache.setAvancement(tache.getAvancement()+i.getAvancement()*i.getAffectation()/100);
        });
        if(tache.getAvancement()==100)
        {
            tache.setDateFinRelle(tache.getDateFinPrevue());
        }
        tacheRepository.save(tache);
    }
}
