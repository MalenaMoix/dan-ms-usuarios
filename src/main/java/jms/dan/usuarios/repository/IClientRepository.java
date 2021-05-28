package jms.dan.usuarios.repository;

import jms.dan.usuarios.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IClientRepository extends JpaRepository<Client, Integer> {

    @Query("SELECT c FROM Client c WHERE c.businessName LIKE %:businessName%")
    List<Client> findAllByBusinessName(@Param("businessName") String businessName);

    Client findByCuit(String cuit);

}
