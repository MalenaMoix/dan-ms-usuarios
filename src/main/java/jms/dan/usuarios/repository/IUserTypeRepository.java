package jms.dan.usuarios.repository;

import jms.dan.usuarios.model.UserType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserTypeRepository extends JpaRepository<UserType, Integer> {
}
