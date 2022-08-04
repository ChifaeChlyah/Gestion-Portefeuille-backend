package com.onee.gestionportefeuilles.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ressource {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long codeRessource;
    String nom;
    String prenom;
    String emploi;
    String nomPhoto;
    @Column(unique = true)
    String email;
    String tel;
    String password;
    @OneToMany(mappedBy = "intervenant")
    @JsonIgnoreProperties("interventions")
    private Collection<Intervention> interventions;
    @OneToMany(mappedBy = "chefProjet")
    @JsonManagedReference
    private Collection<Projet> projets_geres;
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles=new ArrayList<>();
//    @ManyToMany(cascade = CascadeType.ALL)
//    @JsonIgnoreProperties("projetsAffectes")
//    private Collection<Projet> projetsAffectes;
}
