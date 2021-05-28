package jms.dan.usuarios.repository;

import jms.dan.usuarios.model.Client;
import jms.dan.usuarios.model.Construction;
import jms.dan.usuarios.model.ConstructionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IConstructionRepository extends JpaRepository<Construction, Integer> {

    List<Construction> findConstructionByClient(Client client);

    List<Construction> findAllByClientIdAndTypeId(Integer client, Integer type);


}
