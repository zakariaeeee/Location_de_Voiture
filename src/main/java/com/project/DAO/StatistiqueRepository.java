package com.project.DAO;


import com.project.Entities.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

public interface StatistiqueRepository extends JpaRepository<User,Integer> {

    @Procedure(value = "resEnAttent")
    public  Integer count_reservation_enAttent();
    @Procedure(value = "resAccepter")
    public  Integer count_reservation_accepter();
    @Procedure(value = "resRefuser")
    public  Integer count_reservation_refuser();
    @Procedure(value = "resPayer")
    public Integer count_reservation_payer();
    @Procedure(value = "maxRes")
    public Integer max_reservation();
    @Procedure(value = "ResNoPayer")
    public Integer count_reservation_Nopayer();
  //  @Procedure(value = "countVoitureRes")
    //public Integer count_Voiture_reserved();
    @Procedure(value = "resChaqueMoi")
    @Transactional
    public Integer count_res_month(Integer month);
    @Procedure(value = "voitureDesponible")
    public Integer count_voiture_desponible();
    @Procedure(value = "voitureNonDesponible")
    public Integer count_voiture_Nondesponible();
}
