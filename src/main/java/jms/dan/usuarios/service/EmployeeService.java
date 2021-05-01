package jms.dan.usuarios.service;

import jms.dan.usuarios.domain.Employee;
import jms.dan.usuarios.domain.User;
import jms.dan.usuarios.repository.RepositoryEmployee;
import jms.dan.usuarios.repository.RepositoryUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
public class EmployeeService implements IEmployeeService {
    @Autowired
    private RepositoryEmployee repositoryEmployee;
    @Autowired
    private RepositoryUser repositoryUser;

    @Override
    public void createEmployee(Employee employeeToCreate) {
        if (!repositoryUser.isValidUserTypeId(employeeToCreate.getUser().getUserType().getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid user type specified");
        }
        Employee employee = getEmployeeByEmail(employeeToCreate.getMail());
        if (employee != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "An employee with this email already exists");
        }

        Employee newEmployee = new Employee();
        newEmployee.setMail(employeeToCreate.getMail());

        User newUser = repositoryUser.createUser(employeeToCreate.getUser(), employeeToCreate.getUser().getUserType());
        newEmployee.setUser(newUser);

        repositoryEmployee.saveEmployee(newEmployee);
    }

    @Override
    public Employee updateEmployee(Integer id, Employee employeeToUpdate) {
        if (!repositoryUser.isValidUserTypeId(employeeToUpdate.getUser().getUserType().getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid user type specified");
        }
        Employee employee = getEmployeeByEmail(employeeToUpdate.getMail());
        if (employee != null && !employee.getId().equals(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "An employee with this email already exists");
        }

        Employee newEmployee = new Employee();
        newEmployee.setId(id);
        newEmployee.setMail(employeeToUpdate.getMail());
        newEmployee.setUser(employeeToUpdate.getUser());

        Employee employeeUpdated = repositoryEmployee.updateEmployee(id, newEmployee);
        if (employeeUpdated == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found");

        return employeeUpdated;
    }

    @Override
    public List<Employee> getEmployees(String name) {
        List<Employee> employees;
        if (name != null) {
            employees = repositoryEmployee.getEmployeesByName(name);
        } else {
            employees = repositoryEmployee.getAllEmployees();
        }

        return employees;
    }

    @Override
    public void deleteEmployee(Integer id) {
        Employee employee = repositoryEmployee.getEmployeeById(id);
        if (employee == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found");
        repositoryEmployee.deleteEmployee(id);
    }

    @Override
    public Employee getEmployeeById(Integer id) {
        Employee employee = repositoryEmployee.getEmployeeById(id);
        if (employee == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found");
        return employee;
    }

    @Override
    public Employee getEmployeeByEmail(String email) {
        return repositoryEmployee.getEmployeeByEmail(email);
    }
}
