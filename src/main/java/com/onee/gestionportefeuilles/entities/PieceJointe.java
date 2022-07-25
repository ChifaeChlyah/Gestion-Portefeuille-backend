package com.onee.gestionportefeuilles.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PieceJointe {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long idPieceJointe;
    String nom;
    String description;
    String chemin;
}
