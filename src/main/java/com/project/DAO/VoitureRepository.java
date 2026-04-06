package com.project.DAO;

import com.project.Entities.Voiture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

import java.util.List;

public interface VoitureRepository extends JpaRepository<Voiture, String> {
    public Voiture findByMatricule(String matricule);
    public List<Voiture> findByDisponibilite(String disponibilite);
    @Procedure(value = "updateVoitureDes")
    public Integer updateVoitureDes();
    @Procedure(value = "updateVoitureNonDes")
    public Integer updateVoitureNonDes();
}
