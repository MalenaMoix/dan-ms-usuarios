package jms.dan.usuarios.service;

import jms.dan.usuarios.domain.Employee;
import jms.dan.usuarios.domain.User;
import jms.dan.usuarios.domain.UserType;
import jms.dan.usuarios.dto.EmployeeDTO;
import jms.dan.usuarios.dto.EmployeeMapper;
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
    public void createEmployee(EmployeeDTO employeeDTO) {
        if (!isValidUserTypeId(employeeDTO.getUserTypeId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid user type specified");
        }
        EmployeeDTO employee = getEmployeeByEmail(employeeDTO.getMail());
        if (employee != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "An employee with this email already exists");
        }

        Employee newEmployee = new Employee();
        newEmployee.setMail(employeeDTO.getMail());

        User user = createUser(employeeDTO);
        newEmployee.setUser(user);

        EmployeeMapper.toEmployeeDTO(repoEmployee.saveEmployee(newEmployee));
    }

    @Override
    public EmployeeDTO updateEmployee(Integer id, EmployeeDTO employeeDTO) {
        if (!isValidUserTypeId(employeeDTO.getUserTypeId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid user type specified");
        }
        EmployeeDTO employee = getEmployeeByEmail(employeeDTO.getMail());
        if (employee != null && !employee.getId().equals(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "An employee with this email already exists");
        }

        Employee newEmployee = new Employee();
        newEmployee.setId(id);
        newEmployee.setMail(employeeDTO.getMail());

        User user = createUser(employeeDTO);
        newEmployee.setUser(user);

        Employee employeeUpdated = repoEmployee.updateEmployee(id, newEmployee);
        if (employeeUpdated == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found");

        return EmployeeMapper.toEmployeeDTO(employeeUpdated);
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
        Boolean isDeleted = repoEmployee.deleteEmployee(id);
        if (!isDeleted) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found");;
        return true;
    }

    @Override
    public EmployeeDTO getEmployeeById(Integer id) {
        Employee employee = repoEmployee.getEmployeeById(id);
        if (employee == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found");
        return EmployeeMapper.toEmployeeDTO(employee);
    }

    @Override
    public EmployeeDTO getEmployeeByEmail(String email) {
        Employee employee = repoEmployee.getEmployeeByEmail(email);
        if (employee != null) {
            return EmployeeMapper.toEmployeeDTO(employee);
        }
        return null;
    }

    @Override
    public Boolean isValidUserTypeId(Integer userTypeId) {
        OptionalInt indexOpt = IntStream.range(0, userTypes.size()).filter(i -> userTypes.get(i).getId().equals(userTypeId)).findFirst();
        return indexOpt.isPresent();
    }
}
