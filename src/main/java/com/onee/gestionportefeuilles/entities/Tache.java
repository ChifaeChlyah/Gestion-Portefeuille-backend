package com.onee.gestionportefeuilles.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tache implements Serializable {
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
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Collection<Tache> tachesFilles;
    @ManyToMany(mappedBy="dependances")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Collection<Tache> tachesDependantes;
    @ManyToMany
    private Collection<Tache> dependances;
    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Projet projet;
    @OneToMany(mappedBy = "tache")
    private Collection<Intervention> interventions;
    @OneToMany(mappedBy = "tache")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Collection<Activite> activites;
}
