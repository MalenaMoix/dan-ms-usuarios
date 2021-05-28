package jms.dan.usuarios.repository;

import jms.dan.usuarios.model.ConstructionType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IConstructionTypeRepository extends JpaRepository<ConstructionType, Integer> {
}
