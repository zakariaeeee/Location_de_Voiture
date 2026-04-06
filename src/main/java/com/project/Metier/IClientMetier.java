package com.project.Metier;

import com.project.Entities.Admin;
import com.project.Entities.Client;

import java.io.Serializable;
import java.util.List;

public interface IClientMetier extends Serializable {
    public List<Client> getAll();
    public void add(Client client);
    public void update(Client client);
    public void delete(int id);

    public Client getClient(int id);

    public List<Client> getLogin(String login);


    public Client getbyloginandpass(String login, String password);

}
