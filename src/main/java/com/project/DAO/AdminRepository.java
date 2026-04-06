package com.project.DAO;

import com.project.Entities.Admin;
import com.project.Entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
    public Admin findById(int id);
    public Admin findByLoginAndPassword(String login,String password);
    public List<Admin> findByLogin(String login);

}
