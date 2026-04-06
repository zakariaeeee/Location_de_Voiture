package com.project.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "Role",length = 10, discriminatorType = DiscriminatorType.STRING)
@Data
@NoArgsConstructor @AllArgsConstructor @ToString
public abstract class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int id;
    private  String nom;
    private  String prenom;
    private  String email;
    private  String login;
    private  String password;
    private  String telephone;
    private  String image;


}
