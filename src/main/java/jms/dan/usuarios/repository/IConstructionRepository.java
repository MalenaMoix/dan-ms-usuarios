package jms.dan.usuarios.repository;

import jms.dan.usuarios.model.Client;
import jms.dan.usuarios.model.Construction;
import jms.dan.usuarios.model.ConstructionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IConstructionRepository extends JpaRepository<Construction, Integer> {

    List<Construction> findConstructionByClient(Client client);

    @Query("SELECT c FROM Construction c WHERE (c.client.id = :clientId AND c.type.id = :type) OR (:clientId IS NULL AND c.type.id = :type) OR (c.client.id = :clientId AND :type IS NULL) OR (:clientId IS NULL AND :type IS NULL)")
    List<Construction> findAllByClientIdAndTypeId(@Param("clientId") Integer client, @Param("type") Integer type);

}
