package jms.dan.usuarios.service;

import jms.dan.usuarios.domain.Employee;
import jms.dan.usuarios.domain.User;
import jms.dan.usuarios.exceptions.ApiException;
import jms.dan.usuarios.repository.RepositoryEmployee;
import jms.dan.usuarios.repository.RepositoryUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EmployeeService implements IEmployeeService {
    final RepositoryEmployee repositoryEmployee;
    final RepositoryUser repositoryUser;

    @Autowired
    public EmployeeService(RepositoryEmployee repositoryEmployee, RepositoryUser repositoryUser) {
        this.repositoryEmployee = repositoryEmployee;
        this.repositoryUser = repositoryUser;
    }

    @Override
    public void createEmployee(Employee employeeToCreate) {
        if (!repositoryUser.isValidUserTypeId(employeeToCreate.getUser().getUserType().getId())) {
            throw new ApiException(HttpStatus.BAD_REQUEST.toString(), "Invalid user type specified", HttpStatus.BAD_REQUEST.value());
        }
        Employee employee = getEmployeeByEmail(employeeToCreate.getMail());
        if (employee != null) {
            throw new ApiException(HttpStatus.BAD_REQUEST.toString(), "An employee with this email already exists", HttpStatus.BAD_REQUEST.value());
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
            throw new ApiException(HttpStatus.BAD_REQUEST.toString(), "Invalid user type specified", HttpStatus.BAD_REQUEST.value());
        }
        Employee employee = getEmployeeByEmail(employeeToUpdate.getMail());
        if (employee != null && !employee.getId().equals(id)) {
            throw new ApiException(HttpStatus.BAD_REQUEST.toString(), "An employee with this email already exists", HttpStatus.BAD_REQUEST.value());
        }

        Employee newEmployee = new Employee();
        newEmployee.setId(id);
        newEmployee.setMail(employeeToUpdate.getMail());
        newEmployee.setUser(employeeToUpdate.getUser());

        Employee employeeUpdated = repositoryEmployee.updateEmployee(id, newEmployee);
        if (employeeUpdated == null) {
            throw new ApiException(HttpStatus.NOT_FOUND.toString(), "Employee not found", HttpStatus.NOT_FOUND.value());
        }

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
        if (employee == null) {
            throw new ApiException(HttpStatus.NOT_FOUND.toString(), "Employee not found", HttpStatus.NOT_FOUND.value());
        }
        repositoryEmployee.deleteEmployee(id);
    }

    @Override
    public Employee getEmployeeById(Integer id) {
        Employee employee = repositoryEmployee.getEmployeeById(id);
        if (employee == null) {
            throw new ApiException(HttpStatus.NOT_FOUND.toString(), "Employee not found", HttpStatus.NOT_FOUND.value());
        }
        return employee;
    }

    @Override
    public Employee getEmployeeByEmail(String email) {
        return repositoryEmployee.getEmployeeByEmail(email);
    }
}
