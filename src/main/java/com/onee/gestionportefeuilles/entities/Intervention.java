package com.onee.gestionportefeuilles.entities;

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
public class Intervention {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idIntervention;
//    double charge;
    double affectation;
    double avancement;
    // relation entre les tables
    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Tache tache;
    @ManyToOne
    private Ressource intervenant;
}
