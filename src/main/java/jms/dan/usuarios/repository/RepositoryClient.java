package jms.dan.usuarios.repository;

import jms.dan.usuarios.model.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RepositoryClient {
    private static final List<Client> clientsList = new ArrayList<>();
    private static Integer ID_GEN = 1;


    public void createClient(Client newClient) {
        newClient.setId(ID_GEN++);
        clientsList.add(newClient);
    }


    public Client updateClient(Client newClient, Integer id) {
        OptionalInt indexOpt = IntStream.range(0, clientsList.size())
                .filter(i -> clientsList.get(i).getId().equals(id))
                .findFirst();
        if (indexOpt.isPresent() && clientsList.get(indexOpt.getAsInt()).getDischargeDate() == null) {
            clientsList.set(indexOpt.getAsInt(), newClient);
            return newClient;
        } else {
            return null;
        }
    }


    public void deleteClient(Integer id) {
        OptionalInt indexOpt = IntStream.range(0, clientsList.size()).filter(i -> clientsList.get(i).getId().equals(id)).findFirst();
        if (indexOpt.isPresent()) {
            clientsList.remove(indexOpt.getAsInt());
        }
    }


    public Client getClientById(Integer id) {
        OptionalInt indexOpt = IntStream.range(0, clientsList.size()).filter(i -> clientsList.get(i).getId().equals(id)).findFirst();
        if (indexOpt.isPresent() && clientsList.get(indexOpt.getAsInt()).getDischargeDate() == null) {
            return clientsList.get(indexOpt.getAsInt());
        }
        return null;
    }


    public Client getClientByCuit(String cuit) {
        OptionalInt indexOpt = IntStream.range(0, clientsList.size()).filter(i -> clientsList.get(i).getCuit().equals(cuit)).findFirst();
        if (indexOpt.isPresent() && clientsList.get(indexOpt.getAsInt()).getDischargeDate() == null) {
            return clientsList.get(indexOpt.getAsInt());
        }
        return null;
    }


    public List<Client> getClientByBusinessName(String businessName) {
        return clientsList.stream().filter(client -> client.getBusinessName().equals(businessName)
                && client.getDischargeDate() == null).collect(Collectors.toList());
    }


    public List<Client> getAllClients() {
        return clientsList.stream().filter(client -> client.getDischargeDate() == null).collect(Collectors.toList());
    }
}
