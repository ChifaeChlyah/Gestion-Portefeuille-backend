package com.onee.gestionportefeuilles.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tache {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long idTache;
    String titre;
    String description;
    Date dateDebutPlanifiee;
    Date dateDebutPrevue;
    Date dateDebutRelle;
    Date dateFinPlanifiee;
    Date dateFinPrevue;
    Date dateFinRelle;
    double coutInitial;
    double coutReel;
    double avancement;
    //priorite
    //relations avec les tables
    @ManyToOne
    private Tache tacheMere;
    @OneToMany(mappedBy = "tacheMere")
    private Collection<Tache> tachesFilles;
    @ManyToMany(mappedBy="tachesDependantes")
    private Collection<Tache> tachesNecessaires;
    @ManyToMany
    private Collection<Tache> tachesDependantes;
    @ManyToOne
    private Projet projet;
    @OneToMany(mappedBy = "tache")
    private Collection<Intervention> interventions;
}
