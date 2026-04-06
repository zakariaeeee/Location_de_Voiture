package com.project.DAO;

import com.project.Entities.Client;
import com.project.Entities.Paiement;
import com.project.Entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    public Reservation findById(int id);
    public List<Reservation> findByClientAndStatus(Client client, String status);
    public List<Reservation> findByPaiementAndStatus(Paiement paiement, String status);
    public List<Reservation> findAllByStatus(String status);
    public List<Reservation> findByStatusAndPaiement(String status, Paiement paiement);
}
