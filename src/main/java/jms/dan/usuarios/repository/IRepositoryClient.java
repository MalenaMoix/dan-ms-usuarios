package jms.dan.usuarios.repository;

import jms.dan.usuarios.model.Client;
import java.util.List;

public interface IRepositoryClient {
    void createClient(Client newClient);

    Client updateClient(Client newClient, Integer id);

    void deleteClient(Integer id);

    Client getClientById(Integer id);
    Client getClientByCuit(String cuit);
    List<Client> getAllClients();
    List<Client> getClientByBusinessName(String businessName);
}
