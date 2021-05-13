package jms.dan.usuarios.service;

import jms.dan.usuarios.domain.Client;
import java.util.List;

public interface IClientService {
    Client getClientById(Integer id);
    Client getClientByCuit(String cuit);
    List<Client> getClients(String businessName);
    void createClient(Client newClient);
    Client updateClient(Integer id, Client newClient);
    void deleteClient(Integer id);
}
