package com.onee.gestionportefeuilles.entities;

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
    // relation entre les tables
    @ManyToOne
    private Tache tache;
    @ManyToOne
    private Ressource intervenant;
}
