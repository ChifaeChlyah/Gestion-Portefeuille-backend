package com.onee.gestionportefeuilles.entities;

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
    @Column(unique = true)
    String email;
    String tel;
    String password;
    @OneToMany(mappedBy = "intervenant")
    private Collection<Intervention> interventions;
    @OneToMany(mappedBy = "chefProjet")
    private Collection<Projet> projets_geres;
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles=new ArrayList<>();
}
