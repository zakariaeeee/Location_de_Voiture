package com.project.Web;

import com.project.DAO.VoitureRepository;
import com.project.Entities.Admin;
import com.project.Entities.Client;
import com.project.Metier.IAdminMetier;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import java.io.File;
import java.io.IOException;
import java.net.http.HttpRequest;


@Controller
public class AdminController {
    @Autowired
    IAdminMetier iAdminMetier;
    @Autowired
    VoitureRepository voitureRepository;




    @RequestMapping("Admin/Admins")
    public String getAll(Model model)
    {

        voitureRepository.updateVoitureDes();
        voitureRepository.updateVoitureNonDes();
        model.addAttribute("admins",iAdminMetier.getAll());

        return "ListAdmin";
    }
    @GetMapping("Admin/Ajouter_Admin")
    public String addAdmin(@ModelAttribute Admin admin)
    {
        return "AddAdmin";
    }

    @PostMapping("Admin/Ajouter_Admin")
    public String addAdmin1(@ModelAttribute Admin admin,@RequestParam(name= "file") MultipartFile file,Model model) throws IOException
    {
        if(iAdminMetier.getLogin(admin.getLogin()).size() != 0)
        {
            model.addAttribute("eror","Ce login est deja utiliser");
            return "AddAdmin";
        }
        try {
            file.transferTo(new File("C:\\Users\\YASSINE\\Desktop\\EHEI 4 eme anne\\JEE\\TP\\Location_de_Voiture\\src\\main\\resources\\static\\Admin_Pic\\" + file.getOriginalFilename()));
        } catch (IOException e) {
            // Handle the exception
        }

        // Save the file's path to the database
        admin.setImage(file.getOriginalFilename());
        iAdminMetier.add(admin);
        return "AddAdmin";
    }


    @GetMapping("Admin/Modifier_Profil")
    public String update(Model model, HttpServletRequest request)
    {

     HttpSession session=request.getSession();
     int id= (int) session.getAttribute("id");
        model.addAttribute("admin",iAdminMetier.getAdmin(id));
        return "UpdateAdmin";
    }

    @PostMapping("Admin/Modifier_Profil")
    public String update1(@ModelAttribute Admin admin,HttpServletRequest request,Model model) throws IOException
    {

        HttpSession session=request.getSession();
        int id= (int) session.getAttribute("id");


        // Save the file's path to the database
        admin.setId(id);
        iAdminMetier.update(admin);
        return "redirect:/Admin/Mon_Profile";
    }

    @GetMapping("Admin/Supprimer_Admin/{id}")
    public  RedirectView delete(@PathVariable int id){
        iAdminMetier.delete(id);
        return new RedirectView("/Admin/Admins");
    }

    @GetMapping("Admin/Detail_Admin/{id}")
    public  String Detail(Model model,@PathVariable int id){
        Admin admin=iAdminMetier.getAdmin(id);
        model.addAttribute("admin",admin);
        String file = "/cssAdmin/Admin_Pic/"+admin.getImage();

        model.addAttribute("imagePath", file);
        return "DetailAdmin";
    }
    @GetMapping("Admin/Mon_Profile")
    public  String profile(HttpServletRequest request,Model model){
        HttpSession session= request.getSession();
        int id= (int) session.getAttribute("id");
        Admin admin=iAdminMetier.getAdmin(id);
        model.addAttribute("admin",admin);
        String file = "/cssAdmin/Admin_Pic/"+admin.getImage();

        model.addAttribute("imagePath", file);
        return "MyprofileAdmin";
    }


}
