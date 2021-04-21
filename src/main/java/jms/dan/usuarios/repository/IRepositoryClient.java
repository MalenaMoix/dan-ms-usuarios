package jms.dan.usuarios.repository;

import jms.dan.usuarios.domain.Client;

import java.util.List;

public interface IRepositoryClient {
    Client createClient(Client newClient);

    Client updateClient(Client newClient, Integer id);

    Client deleteClient(Integer id);

    Client getClientById(Integer id);
    Client getClientByCuit(String cuit);
    List<Client> getClientByBusinessName(String businessName);
    List<Client> getAllClients();
}
