package jms.dan.usuarios.repository;

import jms.dan.usuarios.domain.Employee;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RepoEmployeeImpl implements RepoEmployee {
    private static final List<Employee> employeesList = new ArrayList<>();
    private static Integer ID_GEN = 1;

    @Override
    public Employee saveEmployee(Employee newEmployee) {
        newEmployee.setId(ID_GEN++);
        employeesList.add(newEmployee);
        return newEmployee;
    }

    @Override
    public Employee updateEmployee(Integer id, Employee newEmployee) {
        // TODO
        return newEmployee;
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeesList;
    }
}
