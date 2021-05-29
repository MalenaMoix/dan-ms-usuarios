package jms.dan.usuarios.service;

import jms.dan.usuarios.exceptions.ApiException;
import jms.dan.usuarios.model.Employee;
import jms.dan.usuarios.model.User;
import jms.dan.usuarios.model.UserType;
import jms.dan.usuarios.repository.IEmployeeRepository;
import jms.dan.usuarios.repository.IUserRepository;
import jms.dan.usuarios.repository.IUserTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService implements IEmployeeService {
    final IEmployeeRepository employeeRepository;
    final IUserRepository userRepository;
    final IUserTypeRepository userTypeRepository;

    @Autowired
    public EmployeeService(IEmployeeRepository employeeRepository, IUserRepository userRepository, IUserTypeRepository userTypeRepository) {
        this.employeeRepository = employeeRepository;
        this.userRepository = userRepository;
        this.userTypeRepository = userTypeRepository;
    }

    @Override
    public void createEmployee(Employee employeeToCreate) {

        UserType userType = userTypeRepository.findById(employeeToCreate.getUser().getUserType().getId()).orElse(null);
        if (userType == null) {
            throw new ApiException(HttpStatus.BAD_REQUEST.toString(),
                    "Invalid user type specified", HttpStatus.BAD_REQUEST.value());
        }
        Employee employee = employeeRepository.findByMail(employeeToCreate.getMail());
        if (employee != null) {
            throw new ApiException(HttpStatus.BAD_REQUEST.toString(), "An employee with this email already exists", HttpStatus.BAD_REQUEST.value());
        }

        Employee newEmployee = new Employee();
        newEmployee.setMail(employeeToCreate.getMail());
        User newUser = new User(employeeToCreate.getUser().getId(), employeeToCreate.getUser().getUser(),
                employeeToCreate.getUser().getPassword(), userType);
        newEmployee.setUser(newUser);
        newEmployee.setName(employeeToCreate.getName());

        employeeRepository.save(newEmployee);
    }

    @Override
    public Employee updateEmployee(Integer id, Employee employeeToUpdate) {
        Employee employee = getEmployeeById(id);
        employee.setMail(employeeToUpdate.getMail());
        employeeRepository.save(employee);
        return employee;
    }

    @Override
    public List<Employee> getEmployees(String name) {
        List<Employee> employees;
        if (name != null) {
            employees = employeeRepository.findAllByName(name);
        } else {
            employees = employeeRepository.findAll();
        }
        return employees;
    }

    @Override
    public void deleteEmployee(Integer id) {
        getEmployeeById(id);
        employeeRepository.deleteById(id);
    }

    @Override
    public Employee getEmployeeById(Integer id) {
        Employee employee = employeeRepository.findById(id).orElse(null);
        if (employee == null) {
            throw new ApiException(HttpStatus.NOT_FOUND.toString(), "Employee not found", HttpStatus.NOT_FOUND.value());
        }
        return employee;
    }
}
