package com.project.Web;
import com.project.Entities.Admin;
import com.project.Metier.IVoitureMetier;
import com.project.Entities.Voiture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import java.io.File;
import java.io.IOException;

@Controller
public class VoitureController {

    @Autowired
    IVoitureMetier ivoitureMetier;

    @RequestMapping("/Admin/Voitures")
    public String getAll(Model model){
       model.addAttribute("voitures", ivoitureMetier.getAll());

        return "Voitures";
    }

    @GetMapping("/Admin/Ajouter_Voiture")

    public String Ajouter(@ModelAttribute Voiture voiture){
        return "AddVoiture";
    }

    @PostMapping("/Admin/Ajouter_Voiture")
    public String Add(@ModelAttribute Voiture voiture, @RequestParam(name= "file") MultipartFile file) throws IOException {

        try {
            file.transferTo(new File("C:\\Users\\YASSINE\\Desktop\\EHEI 4 eme anne\\JEE\\TP\\Location_de_Voiture\\src\\main\\resources\\static\\Admin_Pic\\" + file.getOriginalFilename()));
        } catch (IOException e) {
            // Handle the exception
        }
        voiture.setDisponibilite("true");
        // Save the file's path to the database
        voiture.setImage(file.getOriginalFilename());
        ivoitureMetier.Add(voiture);
        return "AddVoiture";
    }

    @GetMapping("/Admin/Modifier_Voiture/{matricule}")
    public String Update(Model model, @PathVariable String matricule){
        Voiture voiture= ivoitureMetier.getvoiture(matricule);
        model.addAttribute("voiture",voiture);
         return "UpdateVoiture";
    }
    @PostMapping("/Admin/Modifier_Voiture/{matricule}")
    public  RedirectView Modifier(@ModelAttribute Voiture voiture, @PathVariable String matricule,@RequestParam(name= "file") MultipartFile file){
        try {
            file.transferTo(new File("C:\\Users\\YASSINE\\Desktop\\EHEI 4 eme anne\\JEE\\TP\\Location_de_Voiture\\src\\main\\resources\\static\\cssAdmin\\Admin_Pic\\" + file.getOriginalFilename()));
        } catch (IOException e) {
            // Handle the exception
        }
        // Save the file's path to the database
        voiture.setImage(file.getOriginalFilename());
        voiture.setMatricule(matricule);
        ivoitureMetier.update(voiture);
        return new RedirectView("/Admin/Voitures");
    }

    @RequestMapping("/Admin/Supprimer_Voiture/{matricule}")
    public  RedirectView delete(@PathVariable String matricule){
        ivoitureMetier.delete(matricule);
        return new RedirectView("/Admin/Voitures");
    }

    @GetMapping("Admin/Detail_Voiture/{matricule}")
    public  String Detail(Model model,@PathVariable String matricule){
       Voiture voiture=ivoitureMetier.getvoiture(matricule);
        model.addAttribute("voiture",voiture);
        String file = "/cssAdmin/Admin_Pic/"+voiture.getImage();
        // Save the fileString to the database

        // Add the file's path to the model so it can be used in the Thymeleaf template
        model.addAttribute("imagePath", file);
        return "DetailVoiture";
    }
}
