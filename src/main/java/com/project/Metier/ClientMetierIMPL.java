package com.project.Metier;

import com.project.DAO.ClientRepository;
import com.project.Entities.Admin;
import com.project.Entities.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientMetierIMPL implements IClientMetier{

    @Autowired
    ClientRepository clientRepository;

    @Override
    public List<Client> getAll() {
        List<Client> clients=clientRepository.findAll();
        return clients;
    }

    @Override
    public void add(Client client) {
        clientRepository.save(client);
    }

    @Override
    public void update(Client client) {
        clientRepository.save(client);
    }

    @Override
    public void delete(int id) {
        clientRepository.delete(clientRepository.findById(id));
    }

    @Override
    public Client getClient(int id) {
        Client client=clientRepository.findById(id);
        return client;
    }

    @Override
    public List<Client> getLogin(String login) {
        List<Client> cLients = clientRepository.findByLogin(login);
        return cLients;
    }

    @Override
    public Client getbyloginandpass(String login, String password) {
        Client client=clientRepository.findByLoginAndPassword(login, password);
        return client;
    }
}
