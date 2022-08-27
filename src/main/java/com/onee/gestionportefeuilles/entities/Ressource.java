package com.onee.gestionportefeuilles.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
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
    @OneToMany(mappedBy = "intervenant",cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Collection<Intervention> interventions;
    @OneToMany(mappedBy = "intervenant",cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Collection<Intervention> activites;
    @OneToMany(mappedBy = "chefProjet")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Collection<Projet> projets_geres;
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles=new ArrayList<>();
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToMany(mappedBy = "intervenants",cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    private Set<Projet> projetsAffectes=new HashSet<>();
    public void addProjetAffecte(Projet projet)
    {
        projetsAffectes.add(projet);
    }
}
