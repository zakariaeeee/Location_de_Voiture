package com.project.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Entity
@DiscriminatorValue("client")
@Data @NoArgsConstructor @AllArgsConstructor
public class Client extends User{

    private String cin;
    private Date date_de_naissance;
    private String adresse;
    @OneToMany(mappedBy = "client",fetch = FetchType.LAZY)
    private List<Reservation> reservations;
}
