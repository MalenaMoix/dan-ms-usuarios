package jms.dan.usuarios.repository;

import jms.dan.usuarios.domain.Employee;
import java.util.List;

public interface RepoEmployee {
    Employee saveEmployee(Employee newEmployee);
    Employee updateEmployee(Integer id, Employee newEmployee);
    List<Employee> getAllEmployees();
}
