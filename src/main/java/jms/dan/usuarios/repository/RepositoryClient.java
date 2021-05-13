package jms.dan.usuarios.repository;

import jms.dan.usuarios.domain.Client;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RepositoryClient implements IRepositoryClient {
    @Override
    public Client createClient(Client newClient) {
        return null;
    }

    @Override
    public Client updateClient(Client newClient, Integer id) {
        return null;
    }

    @Override
    public Client deleteClient(Integer id) {
        return null;
    }

    @Override
    public Client getClientById(Integer id) {
        return null;
    }

    @Override
    public Client getClientByCuit(String cuit) {
        return null;
    }

    @Override
    public List<Client> getClientByBusinessName(String businessName) {
        return null;
    }

    @Override
    public List<Client> getAllClients() {
        return null;
    }
}
