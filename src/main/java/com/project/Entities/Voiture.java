package com.project.Entities;


import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
@Entity
@Data @NoArgsConstructor @AllArgsConstructor
@ToString
public class Voiture {
    @Id
    private String matricule;
    private String nom;
    private String marque;
    private int model;
    private String type_de_transmition;
    private String type_de_gasol;
    private String image;
    private int capacite;
    private String disponibilite;
    private int prix;
    @OneToMany(mappedBy = "voiture", fetch= FetchType.LAZY)
    private List <Reservation> reservations;

}
