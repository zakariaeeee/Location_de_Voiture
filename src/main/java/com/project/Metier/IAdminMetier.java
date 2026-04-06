package com.project.Metier;

import com.project.Entities.Admin;
import com.project.Entities.Client;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IAdminMetier {
    public List<Admin> getAll();
    public void add(Admin admin) throws IOException;
    public void update(Admin admin);
    public void delete(int id);
    public Admin getAdmin(int id);
    public List<Admin> getLogin(String login);

    public Admin getbyloginandpass(String login,String password);
}
