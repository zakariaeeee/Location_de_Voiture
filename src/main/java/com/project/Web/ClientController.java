package com.project.Web;

import com.project.Entities.Admin;
import com.project.Entities.Client;
import com.project.Metier.IClientMetier;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import java.io.File;
import java.io.IOException;

@Controller
public class ClientController {
    @Autowired
    IClientMetier iClientMetier;

    @RequestMapping("Admin/Clients")
    public String getAll(Model model)
    {
        model.addAttribute("clients",iClientMetier.getAll());
        return "ListClient";
    }

    @GetMapping("Admin/Ajouter_Client")
    public String addClient(@ModelAttribute Client client)
    {
        return "AddClient";
    }

    @PostMapping("Admin/Ajouter_Client")
    public String addClient1(@ModelAttribute Client client,@RequestParam(name= "file") MultipartFile file,Model model) throws IOException
    {
        if(iClientMetier.getLogin(client.getLogin()).size() != 0)
        {
            model.addAttribute("eror","Ce login est deja utiliser");
            return "AddClient";
        }
        try {
            file.transferTo(new File("C:\\Users\\YASSINE\\Desktop\\EHEI 4 eme anne\\JEE\\TP\\Location_de_Voiture\\src\\main\\resources\\static\\cssAdmin\\Admin_Pic\\" + file.getOriginalFilename()));

        } catch (IOException e) {
            // Handle the exception
        }
        client.setImage(file.getOriginalFilename());
        iClientMetier.add(client);
        return "AddClient";
    }

    @GetMapping("Modifier_Profil")
    public String update(@ModelAttribute Client client,HttpServletRequest request ,Model model)
    {
        HttpSession session=request.getSession();
        int id= (int) session.getAttribute("idClient");
        client = iClientMetier.getClient(id);
        model.addAttribute("client",client);
        return "UpdateClient";
    }

    @PostMapping("Modifier_Profil")
    public RedirectView update1(@ModelAttribute Client client,HttpServletRequest request,Model model ) throws IOException
    {

        HttpSession session=request.getSession();
        int id= (int) session.getAttribute("idClient");

        client.setId(id);
        iClientMetier.update(client);
        return new RedirectView("Mon_Profile");
    }

    @GetMapping("Admin/Supprimer_Client/{id}")
    public  RedirectView delete(@PathVariable int id){
        iClientMetier.delete(id);
        return new RedirectView("/Admin/Clients");
    }

    @GetMapping("Admin/Detail_Client/{id}")
    public  String Detail(Model model,@PathVariable int id){
        Client client=iClientMetier.getClient(id);
        model.addAttribute("client",client);
        String file = "/cssAdmin/Admin_Pic/"+client.getImage();
        // Save the fileString to the database

        // Add the file's path to the model so it can be used in the Thymeleaf template
        model.addAttribute("imagePath", file);
        return "DetailClient";
    }
    @GetMapping("Mon_Profile")
    public  String profile(HttpServletRequest request, Model model){
        HttpSession session= request.getSession();
        int idClient= (int) session.getAttribute("idClient");
        Client client=iClientMetier.getClient(idClient);
        model.addAttribute("client",client);
        String file = "/cssAdmin/Admin_Pic/"+client.getImage();

        model.addAttribute("imagePath", file);
        return "MyprofileClient";
    }


}
