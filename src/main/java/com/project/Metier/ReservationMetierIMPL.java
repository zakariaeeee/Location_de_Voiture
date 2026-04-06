package com.project.Metier;

import com.project.DAO.ClientRepository;
import com.project.DAO.PaiementRepository;
import com.project.DAO.ReservationRepository;
import com.project.DAO.VoitureRepository;
import com.project.Entities.Paiement;
import com.project.Entities.Reservation;
import com.project.Entities.Voiture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationMetierIMPL implements IReservationMetier{
    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    PaiementRepository paiementRepository;



    @Override
    public void demandeClient(Reservation reservation) {
        reservationRepository.save(reservation);
    }

    @Override
    public List<Reservation> reservationacepter(int id) {
        List<Reservation> reservations=reservationRepository.findByClientAndStatus(clientRepository.findById(id),"ACCEPTER");
        return  reservations;
    }
    @Override
    public List<Reservation> reservationNomacepter(int id) {
        List<Reservation> reservations=reservationRepository.findByClientAndStatus(clientRepository.findById(id),"REFUSER");
        return  reservations;
    }
    @Override
    public List<Reservation> reservationENAttend(int id) {
        List<Reservation> reservations=reservationRepository.findByClientAndStatus(clientRepository.findById(id),"EN AttENT");
        return reservations;
    }

    @Override
    public List<Reservation> reservationPayer(int id) {

        List<Reservation> reservationsPayer=new ArrayList<>();
        List<Reservation> reservationsAccepter=reservationRepository.findByClientAndStatus(clientRepository.findById(id),"ACCEPTER");
        for(int i=0;i<reservationsAccepter.size();i++){
            if(reservationsAccepter.get(i).getPaiement()!=null){
                reservationsPayer.add(reservationsAccepter.get(i));
            }
        }
        return reservationsPayer;
    }

    @Override
    public Reservation getByID(int id) {
        Reservation reservation=reservationRepository.findById(id);
        return  reservation;
    }

    @Override
    public List<Reservation> reservationNoPayer(int id) {
        List<Reservation> reservationsNoPayer=new ArrayList<>();
        List<Reservation> reservationsAccepter=reservationRepository.findByClientAndStatus(clientRepository.findById(id),"ACCEPTER");
        for(int i=0;i<reservationsAccepter.size();i++){
            if(reservationsAccepter.get(i).getPaiement()==null){
                reservationsNoPayer.add(reservationsAccepter.get(i));
            }
        }
        return reservationsNoPayer;
    }
    @Override
    public void AnnulerReservation(int id) {
        Reservation reservation=reservationRepository.findById(id);
        reservationRepository.delete(reservation);
    }
    @Override
    public void saveReservation(Reservation reservation) {
        reservationRepository.save(reservation);

    }

    @Override
    public List<Reservation> getAllByStatus(String status) {
        List<Reservation> reservations=reservationRepository.findAllByStatus(status);
        return  reservations;
    }

    @Override
    public List<Reservation> getByStatusAndPaiement(String status, Paiement paiement) {
        List<Reservation> reservations=reservationRepository.findByStatusAndPaiement(status,paiement);
        return  reservations;
    }

    @Override
    public int calcule(int id) {
        Reservation reservation=reservationRepository.findById(id);
        Date date1=reservation.getDate_de_debut();
        Date date2=reservation.getDate_de_fin();
        long diff = date2.getTime() - date1.getTime();
        long diffDays = diff / (24 * 60 * 60 * 1000);
        int prix=reservation.getVoiture().getPrix();
        int prixTotal= (int) (diffDays*prix);
        return prixTotal;
    }
}
