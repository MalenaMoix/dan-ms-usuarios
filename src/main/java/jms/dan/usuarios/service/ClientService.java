package jms.dan.usuarios.service;

import jms.dan.usuarios.dto.ClientDTO;
import jms.dan.usuarios.exceptions.ApiException;
import jms.dan.usuarios.model.Client;
import jms.dan.usuarios.model.Construction;
import jms.dan.usuarios.model.User;
import jms.dan.usuarios.model.UserType;
import jms.dan.usuarios.repository.IClientRepository;
import jms.dan.usuarios.repository.IOrderRepository;
import jms.dan.usuarios.repository.IUserRepository;
import jms.dan.usuarios.repository.IUserTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ClientService implements IClientService {
    final IUserRepository userRepository;
    final AccountService accountService;
    final IClientRepository clientRepository;
    final IUserTypeRepository userTypeRepository;
    final IConstructionService constructionService;
    final IOrderRepository orderRepository;

    @Autowired
    public ClientService(IClientRepository clientRepository, IUserRepository userRepository,
                         IUserTypeRepository userTypeRepository, IConstructionService constructionService,
                         AccountService accountService, IOrderRepository orderRepository) {
        this.clientRepository = clientRepository;
        this.userRepository = userRepository;
        this.userTypeRepository = userTypeRepository;
        this.constructionService = constructionService;
        this.accountService = accountService;
        this.orderRepository = orderRepository;
    }

    @Override
    public Client getClientById(Integer id) {
        Client client = clientRepository.findById(id).orElse(null);
        if (client == null) {
            throw new ApiException(HttpStatus.NOT_FOUND.toString(), "Client not found", HttpStatus.NOT_FOUND.value());
        }
        return client;
    }

    @Override
    public Client getClientByCuit(String cuit) {
        Client client = clientRepository.findByCuit(cuit);
        if (client == null) {
            throw new ApiException(HttpStatus.NOT_FOUND.toString(), "Client not found", HttpStatus.NOT_FOUND.value());
        }
        return client;
    }

    @Override
    public List<Client> getClients(String businessName) {
        List<Client> clients;
        if (businessName != null) {
            clients = clientRepository.findAllByBusinessName(businessName);
        } else {
            clients = clientRepository.findAll();
        }
        return clients;
    }

    @Override
    public void createClient(ClientDTO clientDTO) {
        UserType userType = userTypeRepository.findById(clientDTO.getUser().getUserType().getId()).orElse(null);
        if (userType == null) {
            throw new ApiException(HttpStatus.BAD_REQUEST.toString(),
                    "Invalid user type specified", HttpStatus.BAD_REQUEST.value());
        }
        Client client = clientRepository.findByCuit(clientDTO.getCuit());
        if (client != null) {
            throw new ApiException(HttpStatus.BAD_REQUEST.toString(),
                    "A client with CUIT = " + clientDTO.getCuit() + " already exists", HttpStatus.BAD_REQUEST.value());
        }

        Client newClient = new Client();
        newClient.setMail(clientDTO.getMail());
        newClient.setBusinessName(clientDTO.getBusinessName());
        newClient.setCuit(clientDTO.getCuit());
        newClient.setOnlineEnabled(accountService.checkClientCreditSituation(clientDTO.getCuit()));
        newClient.setCurrentBalance(clientDTO.getCurrentBalance());
        newClient.setMaxCurrentAccount(clientDTO.getMaxCurrentAccount());
        User newUser = new User(clientDTO.getUser().getId(), clientDTO.getUser().getUser(),
                clientDTO.getUser().getPassword(), userType);
        newClient.setUser(newUser);

        clientRepository.save(newClient);
    }

    @Override
    public Client updateClient(Integer id, Client clientToUpdate) {
        UserType userType = userTypeRepository.findById(clientToUpdate.getUser().getUserType().getId()).orElse(null);
        if (userType == null) {
            throw new ApiException(HttpStatus.BAD_REQUEST.toString(),
                    "Invalid user type specified", HttpStatus.BAD_REQUEST.value());
        }

        Client client = getClientById(id);
        if (client == null) {
            throw new ApiException(HttpStatus.NOT_FOUND.toString(),
                    "A client with Id = " + id + " not found", HttpStatus.NOT_FOUND.value());
        }

        client.setBusinessName(clientToUpdate.getBusinessName());
        client.setMail(clientToUpdate.getMail());
        client.setMaxCurrentAccount(clientToUpdate.getMaxCurrentAccount());
        client.setOnlineEnabled(clientToUpdate.getOnlineEnabled());
        client.setCurrentBalance(clientToUpdate.getCurrentBalance());
        clientRepository.save(client);

        return client;
    }

    @Override
    public void deleteClient(Integer id) {
        Client client = clientRepository.findById(id).orElse(null);
        if (client == null) {
            throw new ApiException(HttpStatus.NOT_FOUND.toString(), "Client not found", HttpStatus.NOT_FOUND.value());
        }

        if (orderRepository.getOrdersByClientId(id).size() > 0) {
            client.setDischargeDate(LocalDate.now());
            clientRepository.save(client);
        } else {
            constructionService.deleteConstructionsByClient(client);
            clientRepository.deleteById(id);
        }
    }
}