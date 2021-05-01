package jms.dan.usuarios.service;

import jms.dan.usuarios.domain.Client;
import jms.dan.usuarios.domain.User;
import jms.dan.usuarios.repository.RepositoryClient;
import jms.dan.usuarios.repository.RepositoryUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
public class ClientService implements IClientService {
    @Autowired
    private RepositoryClient repositoryClient;
    @Autowired
    private RepositoryUser repositoryUser;

    @Override
    public Client getClientById(Integer id) {
        Client client = repositoryClient.getClientById(id);
        if (client == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found");
        return client;
    }

    @Override
    public Client getClientByCuit(String cuit) {
        Client client = repositoryClient.getClientByCuit(cuit);
        if (client == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found");
        return client;
    }

    @Override
    public List<Client> getClients(String businessName) {
        List<Client> clients;
        if (businessName != null) {
            clients = repositoryClient.getClientByBusinessName(businessName);
        } else {
            clients = repositoryClient.getAllClients();
        }

        return clients;
    }

    @Override
    public void createClient(Client clientToCreate) {
        if (!repositoryUser.isValidUserTypeId(clientToCreate.getUser().getUserType().getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid user type specified");
        }
        Client client = getClientByCuit(clientToCreate.getCuit());
        if (client != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A client with this cuit already exists");
        }
        // TODO check credit situation

        Client newClient = new Client();
        newClient.setMail(clientToCreate.getMail());

        User newUser = repositoryUser.createUser(clientToCreate.getUser(), clientToCreate.getUser().getUserType());
        newClient.setUser(newUser);

        repositoryClient.createClient(clientToCreate);
    }

    @Override
    public Client updateClient(Integer id, Client clientToUpdate) {
        if (!repositoryUser.isValidUserTypeId(clientToUpdate.getUser().getUserType().getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid user type specified");
        }
        Client client = getClientByCuit(clientToUpdate.getCuit());
        if (client != null && !clientToUpdate.getId().equals(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A client with this email already exists");
        }

        Client newClient = new Client();
        newClient.setId(id);
        newClient.setMail(clientToUpdate.getMail());
        newClient.setUser(clientToUpdate.getUser());

        Client clientUpdated = repositoryClient.updateClient(newClient, id);
        if (clientUpdated == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found");

        return clientUpdated;
    }

    @Override
    public void deleteClient(Integer id) {
        Client client = repositoryClient.getClientById(id);
        if (client == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found");
        repositoryClient.deleteClient(id);
    }

    @Override
    public Boolean getCreditSituation() {
        // TODO
        return true;
    }
}
