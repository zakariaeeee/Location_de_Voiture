package com.project.Metier;

import com.project.DAO.AdminRepository;
import com.project.Entities.Admin;
import com.project.Entities.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class AdminMetierIMPL implements IAdminMetier{

    @Autowired
    AdminRepository adminRepository;

    @Override
    public List<Admin> getAll() {
        List<Admin> Admins= adminRepository.findAll();
        return Admins;
    }

    @Override
    public void add(Admin admin) {
       adminRepository.save(admin);
    }

    @Override
    public void update(Admin admin) {
        adminRepository.save(admin);
    }

    @Override
    public void delete(int id) {
        adminRepository.delete(adminRepository.findById(id));
    }

    @Override
    public List<Admin> getLogin(String login) {
        List<Admin> admins = adminRepository.findByLogin(login);
        return admins;
    }

    @Override
    public Admin getAdmin(int id) {
        Admin admin=adminRepository.findById(id);
        return admin;
    }

    @Override
    public Admin getbyloginandpass(String login, String password) {
        Admin admin=adminRepository.findByLoginAndPassword(login, password);
        return admin;
    }
}
