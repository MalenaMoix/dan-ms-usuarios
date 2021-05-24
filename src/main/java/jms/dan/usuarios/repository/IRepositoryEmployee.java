package jms.dan.usuarios.repository;

import jms.dan.usuarios.model.Employee;
import java.util.List;

public interface IRepositoryEmployee {
    void saveEmployee(Employee newEmployee);
    Employee updateEmployee(Integer id, Employee newEmployee);
    void deleteEmployee(Integer id);
    Employee getEmployeeById(Integer id);
    Employee getEmployeeByEmail(String email);

    List<Employee> getAllEmployees();
    List<Employee> getEmployeesByName(String name);
}
