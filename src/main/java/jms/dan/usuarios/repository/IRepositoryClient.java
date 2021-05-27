package jms.dan.usuarios.repository;

import jms.dan.usuarios.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRepositoryClient extends JpaRepository<Client, Integer> {
//    void createClient(Client newClient);

//    Client updateClient(Client newClient, Integer id);

//    void deleteClient(Integer id);

    Client getClientById(Integer id);
    Client getClientByCuit(String cuit);
    List<Client> findAll();
    List<Client> getClientByBusinessName(String businessName);
}
