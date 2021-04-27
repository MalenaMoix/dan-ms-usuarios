package jms.dan.usuarios.service;

import jms.dan.usuarios.dto.EmployeeDTO;
import java.util.List;

public interface IEmployeeService {
    EmployeeDTO createEmployee(EmployeeDTO employeeDTO);
    EmployeeDTO updateEmployee(Integer id, EmployeeDTO employeeDTO);
    Boolean deleteEmployee(Integer id);

    EmployeeDTO getEmployeeById(Integer id);
    EmployeeDTO getEmployeeByEmail(String email);
    List<EmployeeDTO> getEmployees(String name);

    Boolean isValidUserTypeId(Integer userTypeId);
}
