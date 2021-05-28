package jms.dan.usuarios.service;

import jms.dan.usuarios.exceptions.ApiException;
import jms.dan.usuarios.model.Employee;
import jms.dan.usuarios.model.User;
import jms.dan.usuarios.model.UserType;
import jms.dan.usuarios.repository.IEmployeeRepository;
import jms.dan.usuarios.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService implements IEmployeeService {
    final IEmployeeRepository employeeRepository;
    final IUserRepository userRepository;

    @Autowired
    public EmployeeService(IEmployeeRepository employeeRepository, IUserRepository userRepository) {
        this.employeeRepository = employeeRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void createEmployee(Employee employeeToCreate) {
        //TODO CORREGIR!!
//        UserType userType = userTypeRepository.findById(clientDTO.getUser().getUserType().getId()).orElse(null);
//        if (userType == null) {
//            throw new ApiException(HttpStatus.BAD_REQUEST.toString(),
//                    "Invalid user type specified", HttpStatus.BAD_REQUEST.value());
//        }
        Employee employee = getEmployeeByEmail(employeeToCreate.getMail());
        if (employee != null) {
            throw new ApiException(HttpStatus.BAD_REQUEST.toString(), "An employee with this email already exists", HttpStatus.BAD_REQUEST.value());
        }

        Employee newEmployee = new Employee();
        newEmployee.setMail(employeeToCreate.getMail());

//        User newUser = userRepository.createUser(employeeToCreate.getUser(), employeeToCreate.getUser().getUserType());
        User newUser = new User(employeeToCreate.getUser().getId(), employeeToCreate.getUser().getUser(),
                employeeToCreate.getUser().getPassword(), employeeToCreate.getUser().getUserType());
        newEmployee.setUser(newUser);

        employeeRepository.save(newEmployee);
    }

    @Override
    public Employee updateEmployee(Integer id, Employee employeeToUpdate) {
        //TODO CORREGIR!!
//        if (!userRepository.isValidUserTypeId(employeeToUpdate.getUser().getUserType().getId())) {
//            throw new ApiException(HttpStatus.BAD_REQUEST.toString(), "Invalid user type specified", HttpStatus.BAD_REQUEST.value());
//        }
//        Employee employee = getEmployeeByEmail(employeeToUpdate.getMail());
//        if (employee != null && !employee.getId().equals(id)) {
//            throw new ApiException(HttpStatus.BAD_REQUEST.toString(), "An employee with this email already exists", HttpStatus.BAD_REQUEST.value());
//        }
//
//        Employee newEmployee = new Employee();
//        newEmployee.setId(id);
//        newEmployee.setMail(employeeToUpdate.getMail());
//        newEmployee.setUser(employeeToUpdate.getUser());
//
//        Employee employeeUpdated = employeeRepository.updateEmployee(id, newEmployee);
//        if (employeeUpdated == null) {
//            throw new ApiException(HttpStatus.NOT_FOUND.toString(), "Employee not found", HttpStatus.NOT_FOUND.value());
//        }
//
//        return employeeUpdated;
        return null;
    }

    @Override
    public List<Employee> getEmployees(String name) {
        List<Employee> employees;
//        if (name != null) {
//            employees = employeeRepository.getEmployeesByName(name);
//        } else {
//            employees = employeeRepository.findAll();
//        }
//        return employees;
        return null;
    }

    @Override
    public void deleteEmployee(Integer id) {
//        Employee employee = employeeRepository.getEmployeeById(id);
//        if (employee == null) {
//            throw new ApiException(HttpStatus.NOT_FOUND.toString(), "Employee not found", HttpStatus.NOT_FOUND.value());
//        }
//        employeeRepository.deleteEmployee(id);
    }

    @Override
    public Employee getEmployeeById(Integer id) {
//        Employee employee = employeeRepository.getEmployeeById(id);
//        if (employee == null) {
//            throw new ApiException(HttpStatus.NOT_FOUND.toString(), "Employee not found", HttpStatus.NOT_FOUND.value());
//        }
//        return employee;
        return null;
    }

    @Override
    public Employee getEmployeeByEmail(String email) {
        //return employeeRepository.getEmployeeByEmail(email);
        return null;
    }
}
