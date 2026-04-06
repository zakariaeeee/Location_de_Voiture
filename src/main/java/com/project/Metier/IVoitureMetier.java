package com.project.Metier;

import com.project.Entities.Voiture;

import java.util.List;

public interface IVoitureMetier {
    public List<Voiture> getAll();
    public void  Add(Voiture voiture);
    public void update(Voiture voiture);
    public  void delete(String matricule);
    public  Voiture getvoiture(String matricule);

    public  List<Voiture> getByDispo(String dispo);

}
