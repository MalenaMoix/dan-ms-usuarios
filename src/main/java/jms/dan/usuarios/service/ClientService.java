package jms.dan.usuarios.service;

import jms.dan.usuarios.model.Client;
import jms.dan.usuarios.model.User;
import jms.dan.usuarios.dto.ClientDTO;
import jms.dan.usuarios.dto.OrderDTO;
import jms.dan.usuarios.exceptions.ApiException;
import jms.dan.usuarios.repository.RepositoryClient;
import jms.dan.usuarios.repository.RepositoryUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import java.time.LocalDate;
import java.util.List;

@Service
public class ClientService implements IClientService {
    final RepositoryUser repositoryUser;
    final AccountService accountService;
    final RepositoryClient repositoryClient;

    @Autowired
    public ClientService(RepositoryClient repositoryClient, RepositoryUser repositoryUser, AccountService accountService) {
        this.repositoryClient = repositoryClient;
        this.repositoryUser = repositoryUser;
        this.accountService = accountService;
    }

    @Override
    public Client getClientById(Integer id) {
        Client client = repositoryClient.getClientById(id);
        if (client == null) {
            throw new ApiException(HttpStatus.NOT_FOUND.toString(), "Client not found", HttpStatus.NOT_FOUND.value());
        }
        return client;
    }

    @Override
    public Client getClientByCuit(String cuit) {
        Client client = repositoryClient.getClientByCuit(cuit);
        if (client == null) {
            throw new ApiException(HttpStatus.NOT_FOUND.toString(), "Client not found", HttpStatus.NOT_FOUND.value());
        }
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
    public void createClient(ClientDTO clientDTO) {
        if (!repositoryUser.isValidUserTypeId(clientDTO.getUser().getUserType().getId())) {
            throw new ApiException(HttpStatus.BAD_REQUEST.toString(), "Invalid user type specified", HttpStatus.BAD_REQUEST.value());
        }
        Client client = repositoryClient.getClientByCuit(clientDTO.getCuit());
        if (client != null) {
            throw new ApiException(HttpStatus.BAD_REQUEST.toString(), "A client with this cuit already exists", HttpStatus.BAD_REQUEST.value());
        }

        Client newClient = new Client();
        newClient.setMail(clientDTO.getMail());
        newClient.setBusinessName(clientDTO.getBusinessName());
        newClient.setCuit(clientDTO.getCuit());
        newClient.setOnlineEnabled(accountService.checkClientCreditSituation(clientDTO.getCuit()));
        newClient.setCurrentBalance(clientDTO.getCurrentBalance());
        newClient.setMaxCurrentAccount(clientDTO.getMaxCurrentAccount());
        User newUser = repositoryUser.createUser(clientDTO.getUser(), clientDTO.getUser().getUserType());
        newClient.setUser(newUser);

        repositoryClient.createClient(newClient);
    }

    @Override
    public Client updateClient(Integer id, Client clientToUpdate) {
        if (!repositoryUser.isValidUserTypeId(clientToUpdate.getUser().getUserType().getId())) {
            throw new ApiException(HttpStatus.BAD_REQUEST.toString(), "Invalid user type specified", HttpStatus.BAD_REQUEST.value());
        }
        Client client = getClientByCuit(clientToUpdate.getCuit());
        if (client != null && !clientToUpdate.getId().equals(id)) {
            throw new ApiException(HttpStatus.BAD_REQUEST.toString(), "A client with this cuit already exists", HttpStatus.BAD_REQUEST.value());
        }

        Client clientUpdated = repositoryClient.updateClient(clientToUpdate, id);
        if (clientUpdated == null) {
            throw new ApiException(HttpStatus.NOT_FOUND.toString(), "Client not found", HttpStatus.NOT_FOUND.value());
        }

        return clientUpdated;
    }

    @Override
    public void deleteClient(Integer id) {
        Client client = repositoryClient.getClientById(id);
        if (client == null) {
            throw new ApiException(HttpStatus.NOT_FOUND.toString(), "Client not found", HttpStatus.NOT_FOUND.value());
        }

        WebClient webClient = WebClient.create("http://localhost:8081/api-orders/orders?clientId=" + id);
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
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR.toString(), "An error has occurred", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }
}
