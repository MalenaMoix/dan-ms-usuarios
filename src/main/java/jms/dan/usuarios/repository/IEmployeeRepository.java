package jms.dan.usuarios.repository;

import jms.dan.usuarios.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEmployeeRepository extends JpaRepository<Employee, Integer> {

}
