package com.project.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Date;
@Entity
@Data @AllArgsConstructor @NoArgsConstructor @ToString
public class Reservation {
    @Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Date date_de_reservation;
    private Date date_de_debut;
    private Date date_de_fin;
    private String status;
    @ManyToOne
    @JoinColumn(name="id_voiture")
    private Voiture voiture;

    @OneToOne
    @JoinColumn(name = "id_paiement")
    private Paiement paiement;
    @ManyToOne
    @JoinColumn(name ="id_admin")
    private Admin admin;
    @ManyToOne
    @JoinColumn(name ="id_client")
    private Client client;
}
