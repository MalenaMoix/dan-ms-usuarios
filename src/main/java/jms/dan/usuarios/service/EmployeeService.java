package jms.dan.usuarios.service;

import jms.dan.usuarios.domain.Employee;
import jms.dan.usuarios.domain.User;
import jms.dan.usuarios.domain.UserType;
import jms.dan.usuarios.dto.EmployeeDTO;
import jms.dan.usuarios.dto.EmployeeMapper;
import jms.dan.usuarios.repository.RepositoryEmployee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService implements IEmployeeService {
    @Autowired
    private RepositoryEmployee repoEmployee;
    private List<UserType> userTypes;

    @PostConstruct
    private void init() {
        userTypes = new ArrayList<>();
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
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        // TODO check if employee already exists with the email
        // Create method to getEmployeeByEmail
        if (employeeDTO.getUserTypeId().equals(2)) {
            Employee newEmployee = new Employee();
            newEmployee.setMail(employeeDTO.getMail());

            User user = createUser(employeeDTO);
            newEmployee.setUser(user);

            return EmployeeMapper.toEmployeeDTO(repoEmployee.saveEmployee(newEmployee));
        }
        return null;
    }

    @Override
    public EmployeeDTO updateEmployee(Integer id, EmployeeDTO employeeDTO) {
        if (employeeDTO.getUserTypeId().equals(2)) {
            Employee newEmployee = new Employee();
            newEmployee.setId(id);
            newEmployee.setMail(employeeDTO.getMail());

            User user = createUser(employeeDTO);
            newEmployee.setUser(user);

            Employee employeeUpdated = repoEmployee.updateEmployee(id, newEmployee);
            if (employeeUpdated != null) {
                return EmployeeMapper.toEmployeeDTO(employeeUpdated);
            }
            return null;
        }
        return null;
    }

    // TODO implement User methods on its corresponding Repository
    // for now I did it here so we can continue
    private User createUser(EmployeeDTO employeeDTO) {
        User user = new User();
        user.setUser(employeeDTO.getUser().getUser());
        user.setPassword(employeeDTO.getUser().getPassword());

        UserType userType = new UserType();
        userType.setId(employeeDTO.getUserTypeId());
        userType.setType("EMPLOYEE");

        user.setUserType(userType);
        return user;
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
