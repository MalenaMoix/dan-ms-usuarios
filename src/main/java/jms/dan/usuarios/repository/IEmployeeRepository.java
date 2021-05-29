package jms.dan.usuarios.repository;

import jms.dan.usuarios.model.Client;
import jms.dan.usuarios.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IEmployeeRepository extends JpaRepository<Employee, Integer> {

    Employee findByMail(String mail);

    @Query("SELECT e FROM Employee e WHERE e.name LIKE %:name%")
    List<Employee> findAllByName(@Param("name") String name);
}
