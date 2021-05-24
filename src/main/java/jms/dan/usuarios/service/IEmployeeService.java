package jms.dan.usuarios.service;

import jms.dan.usuarios.model.Employee;
import java.util.List;

public interface IEmployeeService {
    void createEmployee(Employee newEmployee);
    Employee updateEmployee(Integer id, Employee newEmployee);
    void deleteEmployee(Integer id);

    Employee getEmployeeById(Integer id);
    Employee getEmployeeByEmail(String email);
    List<Employee> getEmployees(String name);
}
