package com.onee.gestionportefeuilles.entities;

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
    String nom;
    @Enumerated(EnumType.STRING)
    SeveriteRisque severite;
    double probabilite;
    //relations entre les tables
    @ManyToOne
    Projet projet;
}
