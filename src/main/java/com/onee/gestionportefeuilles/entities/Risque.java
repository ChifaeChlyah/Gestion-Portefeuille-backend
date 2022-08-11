package com.onee.gestionportefeuilles.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.onee.gestionportefeuilles.entities.enums.SeveriteRisque;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Risque {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    Long idRisque;
    String titre;
    String severite;
    String description;
    double probabilite;
    //relations entre les tables
    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    Projet projet;
}
