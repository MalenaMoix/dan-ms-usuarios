package jms.dan.usuarios.repository;

import jms.dan.usuarios.domain.Employee;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.IntStream;

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
        OptionalInt indexOpt = IntStream.range(0, employeesList.size())
                .filter(i -> employeesList.get(i).getId().equals(id))
                .findFirst();
        if (indexOpt.isPresent()){
            employeesList.set(indexOpt.getAsInt(), newEmployee);
            return newEmployee;
        } else {
            return null;
        }
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeesList;
    }

    @Override
    public Boolean deleteEmployee(Integer id) {
        OptionalInt indexOpt = IntStream.range(0, employeesList.size()).filter(i -> employeesList.get(i).getId().equals(id)).findFirst();
        if (indexOpt.isPresent()) {
            employeesList.remove(indexOpt.getAsInt());
            return true;
        }
        return false;
    }

    @Override
    public Employee getEmployeeById(Integer id) {
        OptionalInt indexOpt = IntStream.range(0, employeesList.size()).filter(i -> employeesList.get(i).getId().equals(id)).findFirst();
        if (indexOpt.isPresent()) {
            return employeesList.get(indexOpt.getAsInt());
        }
        return null;
    }
}
