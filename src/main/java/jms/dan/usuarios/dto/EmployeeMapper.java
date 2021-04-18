package jms.dan.usuarios.dto;

import jms.dan.usuarios.domain.Employee;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {
    public static EmployeeDTO toEmployeeDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(employee.getId());
        employeeDTO.setMail(employee.getMail());
        employeeDTO.setUser(employee.getUser());
        return employeeDTO;
    }
}
