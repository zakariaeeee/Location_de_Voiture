package com.project.DAO;

import com.project.Entities.Admin;
import com.project.Entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Integer> {
    public Client findById(int id);
    public Client findByLoginAndPassword(String login, String password);

    public List<Client> findByLogin(String login);

}
