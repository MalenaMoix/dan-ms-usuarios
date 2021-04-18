package jms.dan.usuarios.service;

import jms.dan.usuarios.domain.Employee;
import jms.dan.usuarios.domain.User;
import jms.dan.usuarios.domain.UserType;
import jms.dan.usuarios.dto.EmployeeDTO;
import jms.dan.usuarios.dto.EmployeeMapper;
import jms.dan.usuarios.repository.RepoEmployeeImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private RepoEmployeeImpl repoEmployee;

    @Override
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
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
    public List<EmployeeDTO> getAllEmployees() {
        List<Employee> employees = repoEmployee.getAllEmployees();
        List<EmployeeDTO> employeesMapped = new ArrayList<>();
        for (Employee employee : employees) {
            employeesMapped.add(EmployeeMapper.toEmployeeDTO(employee));
        }

        return employeesMapped;
    }

    @Override
    public EmployeeDTO updateEmployee(Integer id, EmployeeDTO employeeDTO) {
        return null;
    }

    @Override
    public EmployeeDTO deleteEmployee(Integer id) {
        return null;
    }

    @Override
    public EmployeeDTO getEmployeeById(Integer id) {
        return null;
    }
}
