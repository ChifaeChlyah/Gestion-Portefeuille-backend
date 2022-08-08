package com.onee.gestionportefeuilles.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.onee.gestionportefeuilles.entities.enums.PrioriteProjet;
import com.onee.gestionportefeuilles.entities.enums.StatutProjet;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.*;

@Entity
@Setter
@Getter
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
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Collection<Risque> risques;
    @OneToMany(mappedBy = "projet")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Collection<Tache> taches;
    @ManyToOne
    private Ressource chefProjet;
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    private Set<Ressource> intervenants=new HashSet<>();
    @OneToMany(mappedBy = "projet")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<PieceJointe> pieceJointes=new HashSet<>();
}
