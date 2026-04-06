package com.project.Web;

import com.project.Entities.Admin;
import com.project.Entities.Client;
import com.project.Metier.IAdminMetier;
import com.project.Metier.IClientMetier;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
public class LoginController {
    @Autowired
    IClientMetier iClientMetier;
    @Autowired
    IAdminMetier iAdminMetier;

    @RequestMapping("/Login_Admin")
    public String login(@ModelAttribute Admin admin)
    {
        return "AdminLogin";
    }

    @PostMapping("/Login_Admin")
    public String login1(HttpServletRequest request, @ModelAttribute Admin admin, Model model)
    {
        Admin a=iAdminMetier.getbyloginandpass(admin.getLogin(), admin.getPassword());
        if(a == null) {
            model.addAttribute("eror", "login ou mot de passe incorrect");
            return "AdminLogin";
        }
        else {
            HttpSession session= request.getSession(true);
            session.setAttribute("id",a.getId());
            return "redirect:/Admin/Admins";
        }
    }

    @RequestMapping("/Login_Client")
    public String loginclient(@ModelAttribute Client client)
    {
        return "ClientLogin";
    }

    @PostMapping("/Login_Client")
    public String loginclient1(HttpServletRequest request, @ModelAttribute Client client, Model model)
    {
        Client a=iClientMetier.getbyloginandpass(client.getLogin(), client.getPassword());
        if(a == null) {
            model.addAttribute("eror", "login ou mot de passe incorrect");
            return "ClientLogin";
        }
        else {
            HttpSession session= request.getSession(true);
            session.setAttribute("idClient",a.getId());
            return "redirect:/Liste_des_Voitures";
        }
    }

    @RequestMapping("/Logout")
    public String logout(HttpServletRequest request)
    {
            HttpSession session= request.getSession(true);
            session.invalidate();
            return "redirect:/Login_Client";

    }
    @RequestMapping("/Creer_Compte")
    public String CreerCompote(@ModelAttribute Client client)
    {

        return "AddCompte";

    }

    @PostMapping("/Creer_Compte")
    public String CreerCompote1(@ModelAttribute Client client,@RequestParam(name= "file") MultipartFile file,Model model) throws IOException
    {
        if(iClientMetier.getLogin(client.getLogin()).size() != 0)
        {
            System.out.println("------------------------------------"+client.getLogin());
            model.addAttribute("eror","Ce login est deja utiliser");
            return "AddCompte";
        }

        try {
            file.transferTo(new File("C:\\Users\\YASSINE\\Desktop\\EHEI 4 eme anne\\JEE\\TP\\Location_de_Voiture\\src\\main\\resources\\static\\cssAdmin\\Admin_Pic\\" + file.getOriginalFilename()));

        } catch (IOException e) {
            // Handle the exception
        }
        client.setImage(file.getOriginalFilename());
        iClientMetier.add(client);
        return "redirect:/Login_Client";

    }


}
