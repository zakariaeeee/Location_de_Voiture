package com.project.Web;

import com.project.DAO.PaiementRepository;
import com.project.DAO.VoitureRepository;
import com.project.Entities.*;
import com.project.Metier.*;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Controller
public class ReservationController {
    @Autowired
    IAdminMetier iAdminMetier;
    @Autowired
    IClientMetier iClientMetier;
    @Autowired
    IVoitureMetier iVoitureMetier;
    @Autowired
    IReservationMetier iReservationMetier;

    @Autowired
    IPdfMetier iPdfMetier;
    @Autowired
    VoitureRepository voitureRepository;


    @Autowired
    PaiementRepository paiementRepository;

    @GetMapping("Home")
    public  String Home(Model model ){

            model.addAttribute("voitures", iVoitureMetier.getAll());
            return "Home";
    }

    @RequestMapping("/Liste_des_Voitures")
    public String getAll(Model model)
    {
        voitureRepository.updateVoitureDes();
        voitureRepository.updateVoitureNonDes();
        model.addAttribute("voitures", iVoitureMetier.getByDispo("true"));
        return "ListVoituresClient";
    }

    @RequestMapping("/Voitures_Selectionner/{matricule}")
    public String reserver(Model model, @PathVariable String matricule, @ModelAttribute Reservation reservation)
    {
        Voiture voiture= iVoitureMetier.getvoiture(matricule);
        model.addAttribute("voiture",voiture);
        return "ReservedCar";
    }

    @PostMapping("/Voitures_Selectionner/{matricule}")
    public RedirectView reserver1(HttpServletRequest request,Model model, @PathVariable String matricule, @ModelAttribute Reservation reservation) throws ParseException {
        HttpSession session =request.getSession();
        int idClient= (int) session.getAttribute("idClient");
        Client client=iClientMetier.getClient(idClient);
        reservation.setClient(client);
        Voiture voiture= iVoitureMetier.getvoiture(matricule);
        reservation.setVoiture(voiture);
        LocalDate currentDate = LocalDate.now();
        java.sql.Date sqlDate = java.sql.Date.valueOf(currentDate);
        reservation.setDate_de_reservation(sqlDate);
        reservation.setStatus("EN ATTENT");
        iReservationMetier.demandeClient(reservation);
        return new RedirectView("/Liste_des_Voitures");
    }

    @RequestMapping("/pdfgenerer/{id}")
    public String pdf(HttpServletRequest request,@PathVariable int id)
    {
        try {
            iPdfMetier.generatePDF(request,id);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return "redirect:/pdfgenerer";

    }
    @RequestMapping("/pdfgenerer")
    public String pdf(  )
    {

        return "facture";

    }

    @RequestMapping("/HistoriqueClient")
    public String reservationAccepter(HttpServletRequest request,Model model)
    {
        HttpSession session= request.getSession();
        int id= (int) session.getAttribute("idClient");
        model.addAttribute("reservationacepter",iReservationMetier.reservationacepter(id));
        model.addAttribute("reservationNoAccepter",iReservationMetier.reservationNomacepter(id));
        model.addAttribute("reservationEnAttend",iReservationMetier.reservationENAttend(id));
        model.addAttribute("reservationPayer",iReservationMetier.reservationPayer(id));
        model.addAttribute("reservationNoPayer",iReservationMetier.reservationNoPayer(id));
        return "Liste_reservation_client";
    }
    @RequestMapping("Annuler_reservation/{id}")
    public RedirectView delete(@PathVariable int id){
        iReservationMetier.AnnulerReservation(id);
        return new RedirectView("/HistoriqueClient");
    }
    @RequestMapping("/Admin/Reservation_en_attente")
    public String reservation_en_attente(Model model)
    {

        List<Reservation> reservationsEnAttent= iReservationMetier.getAllByStatus("EN ATTENT");


        if(reservationsEnAttent.isEmpty())
        {
            model.addAttribute("aucunReservations","Pas de réservation pour le moment");
        }

        model.addAttribute("reservations",reservationsEnAttent);



        return "ReservationEnAttente";
    }
    @RequestMapping("/Admin/Reservations_accepter")
    public String reservation_accepter(Model model,@ModelAttribute Admin admin,@ModelAttribute Reservation reservation)
    {

        List<Reservation> reservationAcceptersPayer= new ArrayList<>();
        List<Reservation> reservationsAccpters= iReservationMetier.getAllByStatus("ACCEPTER");
        for(int i=0; i<reservationsAccpters.size();i++)
        {
            if(reservationsAccpters.get(i).getPaiement()!=null)
            {
                reservationAcceptersPayer.add(reservationsAccpters.get(i));
            }

        }
        model.addAttribute("paiyedReservation",reservationAcceptersPayer);


        List<Reservation> reservationAcceptersNonPayer= iReservationMetier.getByStatusAndPaiement("ACCEPTER", null);


        model.addAttribute("reservationsAccepter",reservationsAccpters);
        for(int i=0; i<reservationsAccpters.size();i++)
        {
           System.out.println(reservationsAccpters.get(i).getClient().getNom());

        }
        model.addAttribute("NonpaiyedReservation",reservationAcceptersNonPayer);

        return "Reservation_accepter";
    }

    @GetMapping("/accepter_Reservation/{id}")
    public RedirectView reservation_accepter1(@PathVariable int id,@ModelAttribute Reservation reservation,HttpServletRequest request,@ModelAttribute Admin admin)
    {

        HttpSession session= request.getSession(true);
        int IdAdmin=(int)session.getAttribute("id");


        reservation= iReservationMetier.getByID(id);
        Admin a=iAdminMetier.getAdmin(IdAdmin);
        reservation.setStatus("ACCEPTER");
        reservation.setAdmin(a);

        String v= reservation.getVoiture().getMatricule();
        Voiture m=iVoitureMetier.getvoiture(v);
        m.setDisponibilite("false");


        iReservationMetier.saveReservation(reservation);

        return new RedirectView("/Admin/Reservation_en_attente");
    }
    @RequestMapping("/Admin/Reservation_refuser")
    public String reservation_refuser(Model model, Reservation reservation)
    {
        List<Reservation> reservationsRefuser= iReservationMetier.getAllByStatus("REFUSER");
        model.addAttribute("reservationsRefuser",reservationsRefuser);

        return "Reservation_refuser";
    }

    @GetMapping("/refuser_Reservation/{id}")
    public RedirectView reservation_refuser1(@PathVariable int id,@ModelAttribute Reservation reservation,HttpServletRequest request,@ModelAttribute Admin admin)
    {
        HttpSession session= request.getSession(true);
        int IdAdminN=(int)session.getAttribute("id");
        reservation= iReservationMetier.getByID(id);
        Admin ad=iAdminMetier.getAdmin(IdAdminN);
        reservation.setAdmin(ad);
        reservation.setStatus("REFUSER");
        iReservationMetier.saveReservation(reservation);
        return new RedirectView("/Admin/Reservation_en_attente");

    }

    @RequestMapping("/Payer_reservation/{id}")
    public String payer(@PathVariable int id,Model model,@ModelAttribute Paiement paiement)
    {
        int prix=iReservationMetier.calcule(id);
        model.addAttribute("prix",prix);
        return "Confirmer_payment";

    }
    @PostMapping("/Payer_reservation/{id}")
    public String payer_confirmere(@PathVariable int id, Model model,@ModelAttribute Paiement paiement)
    {
        int prix=iReservationMetier.calcule(id);
        paiement.setCout(prix);
        paiement.setId(id+1);
        paiementRepository.save(paiement);
        Reservation reservation=iReservationMetier.getByID(id);

        reservation.setPaiement(paiement);
        iReservationMetier.demandeClient(reservation);
        return "redirect:/HistoriqueClient";

    }

}
