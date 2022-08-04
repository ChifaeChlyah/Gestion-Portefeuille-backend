package com.onee.gestionportefeuilles.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.onee.gestionportefeuilles.entities.enums.PrioriteProjet;
import com.onee.gestionportefeuilles.entities.enums.StatutProjet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Projet {
    @Id
    String codeProjet;
    String titreProjet;
    String description;
    Date dateDebutPlanifiee;
    Date dateDebutPrevue;
    Date dateDebutReelle;
    Date dateFinPlanifiee;
    Date dateFinPrevue;
    Date dateFinReelle;
    String priorite;
    double avancement;
    double coutInitial;
    double coutReel;
    String logo;
    String statut;
    // relations entre les tables
    @ManyToOne
    private FamilleProjet familleProjet;
    @ManyToMany
    private Collection<Projet> predecesseurs;
    @OneToMany(mappedBy = "projet")
    @JsonIgnore
    private Collection<Risque> risques;
    @OneToMany(mappedBy = "projet")
    @JsonIgnore
    private Collection<Tache> taches;
    @ManyToOne
    @JsonBackReference
    private Ressource chefProjet;
//    @ManyToMany(mappedBy = "projetsAffectes")
//    @LazyCollection(LazyCollectionOption.FALSE)
//    private Collection<Ressource> intervenants=new ArrayList<>();
}
