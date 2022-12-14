package com.onee.gestionportefeuilles.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FamilleProjet
{
    @Id
    String codeFamille;
    String titreFamille;
    String description;
    @JsonIgnoreProperties("familleProjet")
    @OneToMany(mappedBy = "familleProjet")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Collection<Projet> projets;
}
