package com.project.Web;

import com.project.DAO.ReservationRepository;
import com.project.DAO.StatistiqueRepository;
import com.project.DAO.VoitureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.DateFormatSymbols;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

@Controller
public class StatisticsController {
    @Autowired
    StatistiqueRepository statistiqueRepository;
    @Autowired
    VoitureRepository voitureRepository;
    @RequestMapping("/Admin/Statistics")
    public String getAll(Model model)
    {

        voitureRepository.updateVoitureDes();
        voitureRepository.updateVoitureNonDes();
        model.addAttribute("count_reservationEnAttent", statistiqueRepository.count_reservation_enAttent());
        model.addAttribute("count_reservationAccepter", statistiqueRepository.count_reservation_accepter());
        model.addAttribute("count_reservationRefuser", statistiqueRepository.count_reservation_refuser());
        model.addAttribute("count_reservationPayer", statistiqueRepository.count_reservation_payer());
        model.addAttribute("max_reservation", statistiqueRepository.max_reservation());
       model.addAttribute("count_reservationNoPayer", statistiqueRepository.count_reservation_Nopayer());
        //   model.addAttribute("count_Voiture_reserved", statistiqueRepository.count_Voiture_reserved());

        model.addAttribute("month1",statistiqueRepository.count_res_month(1));
        model.addAttribute("month2",statistiqueRepository.count_res_month(2));
        model.addAttribute("month3",statistiqueRepository.count_res_month(3));
        model.addAttribute("month4",statistiqueRepository.count_res_month(4));
        model.addAttribute("month5",statistiqueRepository.count_res_month(5));
        model.addAttribute("month6",statistiqueRepository.count_res_month(6));
        model.addAttribute("month7",statistiqueRepository.count_res_month(7));
        model.addAttribute("month8",statistiqueRepository.count_res_month(8));
        model.addAttribute("month9",statistiqueRepository.count_res_month(9));
        model.addAttribute("month10",statistiqueRepository.count_res_month(10));
        model.addAttribute("month11",statistiqueRepository.count_res_month(11));
        model.addAttribute("month12",statistiqueRepository.count_res_month(12));
        model.addAttribute("j1","janvier");
        model.addAttribute("f2","février");
        model.addAttribute("m3","mars");
        model.addAttribute("a4","avril");
        model.addAttribute("m5","mai");
        model.addAttribute("j6","juin");
        model.addAttribute("ju7","juillet");
        model.addAttribute("au8","août");
        model.addAttribute("sep9","septembre");
        model.addAttribute("oc10","octobre");
        model.addAttribute("no11","novembre");
        model.addAttribute("dec12","décembre");
 
        model.addAttribute("CountVoitureDesponible",statistiqueRepository.count_voiture_desponible());

        model.addAttribute("CountVoitureNondesponible",statistiqueRepository.count_voiture_Nondesponible());


        return "statistics";
    }
}
