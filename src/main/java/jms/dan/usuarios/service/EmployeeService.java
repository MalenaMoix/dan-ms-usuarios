package jms.dan.usuarios.service;

import jms.dan.usuarios.dto.EmployeeDTO;
import java.util.List;

public interface EmployeeService {
    EmployeeDTO createEmployee(EmployeeDTO employeeDTO);
    EmployeeDTO updateEmployee(Integer id, EmployeeDTO employeeDTO);
    Boolean deleteEmployee(Integer id);
    EmployeeDTO getEmployeeById(Integer id);

    List<EmployeeDTO> getAllEmployees();

    // TODO getEmployee by name optional query string
    // Decide how to implement that
}
