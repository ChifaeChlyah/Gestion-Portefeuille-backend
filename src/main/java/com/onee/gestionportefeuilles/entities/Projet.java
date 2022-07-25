package com.onee.gestionportefeuilles.entities;

import com.onee.gestionportefeuilles.entities.enums.PrioriteProjet;
import com.onee.gestionportefeuilles.entities.enums.StatutProjet;
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
    @Enumerated(EnumType.STRING)
    PrioriteProjet priorite;
    double avancement;
    double coutInitial;
    double coutReel;
    String logo;
    @Enumerated(EnumType.STRING)
    StatutProjet statut;
    // relations entre les tables
    @ManyToOne
    private FamilleProjet familleProjet;
    @ManyToMany
    private Collection<Projet> predecesseurs;
    @OneToMany(mappedBy = "projet")
    private Collection<Risque> risques;
    @OneToMany(mappedBy = "projet")
    private Collection<Tache> taches;
    @ManyToOne
    private Ressource chefProjet;
}
