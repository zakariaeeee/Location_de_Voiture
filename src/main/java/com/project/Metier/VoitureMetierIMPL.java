package com.project.Metier;

import com.project.DAO.VoitureRepository;
import com.project.Entities.Voiture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class VoitureMetierIMPL implements IVoitureMetier {
   @Autowired
    VoitureRepository voitureRepository;
    @Override
    public List<Voiture> getAll(){
        List<Voiture> voitures = voitureRepository.findAll();
        return voitures;
    }
    @Override
    public void Add(Voiture voiture){
        voitureRepository.save(voiture);
    }
    @Override
    public  void update(Voiture voiture){
        voitureRepository.save(voiture);
    }
    @Override
    public  void delete(String matricule ){
            Voiture voiture=voitureRepository.findByMatricule(matricule);
            System.out.println(voiture.getNom());
             voitureRepository.delete(voiture);
    }
    @Override
    public  Voiture getvoiture(String matricule){
               Voiture voiture=voitureRepository.findByMatricule(matricule);
               return voiture;
    }

    @Override
    public List<Voiture> getByDispo(String dispo) {
        List<Voiture> voitures=voitureRepository.findByDisponibilite(dispo);
        return voitures;
    }
}
