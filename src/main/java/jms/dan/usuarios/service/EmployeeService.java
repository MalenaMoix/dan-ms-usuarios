package jms.dan.usuarios.service;

import jms.dan.usuarios.domain.Employee;
import jms.dan.usuarios.domain.User;
import jms.dan.usuarios.domain.UserType;
import jms.dan.usuarios.repository.RepositoryEmployee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.IntStream;

@Service
public class EmployeeService implements IEmployeeService {
    @Autowired
    private RepositoryEmployee repoEmployee;
    private List<UserType> userTypes = new ArrayList<>();

    @PostConstruct
    private void init() {
        UserType userType1 = new UserType();
        userType1.setId(1);
        userType1.setType("CLIENT");
        UserType userType2 = new UserType();
        userTypes.add(userType1);
        userType2.setId(2);
        userType2.setType("EMPLOYEE");
        userTypes.add(userType2);
    }

    @Override
    public void createEmployee(Employee employeeToCreate) {
        if (!isValidUserTypeId(employeeToCreate.getUser().getUserType().getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid user type specified");
        }
        Employee employee = getEmployeeByEmail(employeeToCreate.getMail());
        if (employee != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "An employee with this email already exists");
        }

        Employee newEmployee = new Employee();
        newEmployee.setMail(employeeToCreate.getMail());

        User user = createUser(employeeToCreate);
        newEmployee.setUser(user);

        repoEmployee.saveEmployee(newEmployee);
    }

    @Override
    public Employee updateEmployee(Integer id, Employee employeeToUpdate) {
        if (!isValidUserTypeId(employeeToUpdate.getUser().getUserType().getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid user type specified");
        }
        Employee employee = getEmployeeByEmail(employeeToUpdate.getMail());
        if (employee != null && !employee.getId().equals(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "An employee with this email already exists");
        }

        Employee newEmployee = new Employee();
        newEmployee.setId(id);
        newEmployee.setMail(employeeToUpdate.getMail());

        User user = createUser(employeeToUpdate);
        newEmployee.setUser(user);

        Employee employeeUpdated = repoEmployee.updateEmployee(id, newEmployee);
        if (employeeUpdated == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found");

        return employeeUpdated;
    }

    // TODO implement User methods on its corresponding Repository
    // for now I did it here so we can continue
    private User createUser(Employee employee) {
        User user = new User();
        user.setUser(employee.getUser().getUser());
        user.setPassword(employee.getUser().getPassword());

        UserType userType = new UserType();
        userType.setId(employee.getUser().getUserType().getId());
        userType.setType("EMPLOYEE");

        user.setUserType(userType);
        return user;
    }

    @Override
    public List<Employee> getEmployees(String name) {
        List<Employee> employees;
        if (name != null) {
            employees = repoEmployee.getEmployeesByName(name);
        } else {
            employees = repoEmployee.getAllEmployees();
        }

        return employees;
    }

    @Override
    public void deleteEmployee(Integer id) {
        Employee employee = repoEmployee.getEmployeeById(id);
        if (employee == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found");
        repoEmployee.deleteEmployee(id);
    }

    @Override
    public Employee getEmployeeById(Integer id) {
        Employee employee = repoEmployee.getEmployeeById(id);
        if (employee == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found");
        return employee;
    }

    @Override
    public Employee getEmployeeByEmail(String email) {
        return repoEmployee.getEmployeeByEmail(email);
    }

    @Override
    public Boolean isValidUserTypeId(Integer userTypeId) {
        OptionalInt indexOpt = IntStream.range(0, userTypes.size()).filter(i -> userTypes.get(i).getId().equals(userTypeId)).findFirst();
        return indexOpt.isPresent();
    }
}
