package jms.dan.usuarios.service;

import jms.dan.usuarios.domain.Employee;
import jms.dan.usuarios.domain.User;
import jms.dan.usuarios.domain.UserType;
import jms.dan.usuarios.dto.EmployeeDTO;
import jms.dan.usuarios.dto.EmployeeMapper;
import jms.dan.usuarios.repository.RepositoryEmployee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService implements IEmployeeService {
    @Autowired
    private RepositoryEmployee repoEmployee;

    @Override
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        // TODO check TODOs on updateEmployee
        Employee newEmployee = new Employee();
        newEmployee.setMail(employeeDTO.getMail());

        // TODO create User
        User user = new User();
        user.setUser(employeeDTO.getUser().getUser());
        user.setPassword(employeeDTO.getUser().getPassword());

        UserType userType = new UserType();
        userType.setId(2);
        userType.setType("EMPLOYEE");

        user.setUserType(userType);
        newEmployee.setUser(user);

        return EmployeeMapper.toEmployeeDTO(repoEmployee.saveEmployee(newEmployee));
    }

    @Override
    public List<EmployeeDTO> getEmployees(String name) {
        List<Employee> employees;
        if (name != null) {
            employees = repoEmployee.getEmployeesByName(name);
        } else {
            employees = repoEmployee.getAllEmployees();
        }

        List<EmployeeDTO> employeesMapped = new ArrayList<>();
        for (Employee employee : employees) {
            employeesMapped.add(EmployeeMapper.toEmployeeDTO(employee));
        }

        return employeesMapped;
    }

    @Override
    public EmployeeDTO updateEmployee(Integer id, EmployeeDTO employeeDTO) {
        // TODO check if employee already exists with the email
        // TODO duplicated code, make a function
        Employee newEmployee = new Employee();
        newEmployee.setMail(employeeDTO.getMail());

        // TODO update User
        User user = new User();
        user.setUser(employeeDTO.getUser().getUser());
        user.setPassword(employeeDTO.getUser().getPassword());

        // TODO get type id from request and check if it exists
        UserType userType = new UserType();
        userType.setId(2);
        userType.setType("EMPLOYEE");

        user.setUserType(userType);
        newEmployee.setUser(user);
        Employee employeeUpdated = repoEmployee.updateEmployee(id, newEmployee);
        if (employeeUpdated != null) {
            return EmployeeMapper.toEmployeeDTO(employeeUpdated);
        }
        return null;
    }

    @Override
    public Boolean deleteEmployee(Integer id) {
        return repoEmployee.deleteEmployee((id));
    }

    @Override
    public EmployeeDTO getEmployeeById(Integer id) {
        Employee employee = repoEmployee.getEmployeeById(id);
        if (employee != null) {
            return EmployeeMapper.toEmployeeDTO(employee);
        }
        return null;
    }
}
