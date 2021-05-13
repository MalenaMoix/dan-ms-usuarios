package jms.dan.usuarios.service;

import jms.dan.usuarios.domain.Client;
import jms.dan.usuarios.domain.User;
import jms.dan.usuarios.dto.OrderDTO;
import jms.dan.usuarios.repository.RepositoryClient;
import jms.dan.usuarios.repository.RepositoryUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
public class ClientService implements IClientService {
    @Autowired
    private RepositoryClient repositoryClient;
    @Autowired
    private RepositoryUser repositoryUser;
    @Autowired
    private IAccountService iAccountService;

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
        Client client = repositoryClient.getClientByCuit(clientToCreate.getCuit());
        if (client != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A client with this cuit already exists");
        }

        Client newClient = new Client();
        newClient.setMail(clientToCreate.getMail());
        newClient.setOnlineEnabled(iAccountService.checkClientCreditSituation(clientToCreate.getCuit()));

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

        WebClient webClient = WebClient.create("http://localhost:8080/api/orders");
        try {
            ResponseEntity<List<OrderDTO>> response = webClient.get()
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .toEntityList(OrderDTO.class)
                    .block();

            if (response != null && response.getBody() != null) {
                if (response.getBody().size() > 0) {
                    client.setDischargeDate(LocalDate.now());
                } else {
                    repositoryClient.deleteClient(id);
                }
            }
        } catch (WebClientException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error has occurred");
        }
    }
}
