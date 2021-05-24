package jms.dan.usuarios.service;

import jms.dan.usuarios.model.Client;
import jms.dan.usuarios.dto.ClientDTO;
import java.util.List;

public interface IClientService {
    Client getClientById(Integer id);
    Client getClientByCuit(String cuit);
    List<Client> getClients(String businessName);
    void createClient(ClientDTO clientDTO);
    Client updateClient(Integer id, Client newClient);
    void deleteClient(Integer id);
}
