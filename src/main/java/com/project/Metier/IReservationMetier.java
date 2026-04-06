package com.project.Metier;

import com.project.Entities.Paiement;
import com.project.Entities.Reservation;
import com.project.Entities.Voiture;

import java.util.List;

public interface IReservationMetier {
    public void demandeClient(Reservation reservation);
    public List<Reservation> reservationacepter(int id);
    public List<Reservation> reservationNomacepter(int id);
    public List<Reservation> reservationENAttend(int id);
    public List<Reservation> reservationPayer(int id);
    public Reservation getByID(int id);
    public List<Reservation> reservationNoPayer(int id);

    public void AnnulerReservation(int id);
    public void saveReservation(Reservation reservation);
    public List<Reservation> getAllByStatus(String status);

    public List<Reservation> getByStatusAndPaiement(String status, Paiement paiement);
    public int calcule(int id);



}
