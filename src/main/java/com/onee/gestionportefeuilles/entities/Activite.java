package com.onee.gestionportefeuilles.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Activite {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     Long idActivite;
     String description;
     Date date;
     @ManyToOne
     Tache tache;

     @ManyToOne
     Ressource intervenant;
}
