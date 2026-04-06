package com.project.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@DiscriminatorValue("admin")
@Data @NoArgsConstructor @AllArgsConstructor
public class Admin extends  User{

    private String permision;
    @OneToMany(mappedBy = "admin",fetch = FetchType.LAZY)
    private List<Reservation> reservations;
}
