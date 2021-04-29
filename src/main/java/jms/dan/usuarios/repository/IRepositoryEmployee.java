package jms.dan.usuarios.repository;

import jms.dan.usuarios.domain.Employee;
import java.util.List;

public interface IRepositoryEmployee {
    Employee saveEmployee(Employee newEmployee);
    Employee updateEmployee(Integer id, Employee newEmployee);
    Boolean deleteEmployee(Integer id);
    Employee getEmployeeById(Integer id);
    Employee getEmployeeByEmail(String email);

    List<Employee> getAllEmployees();
    List<Employee> getEmployeesByName(String name);
}
