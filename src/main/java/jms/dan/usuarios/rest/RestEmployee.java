package jms.dan.usuarios.rest;

import jms.dan.usuarios.dto.EmployeeDTO;
import jms.dan.usuarios.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class RestEmployee {
    @Autowired
    private IEmployeeService IEmployeeService;

    @PostMapping
    public ResponseEntity<String> createEmployee(@RequestBody EmployeeDTO newEmployee) {
        EmployeeDTO employee = IEmployeeService.getEmployeeByEmail(newEmployee.getMail());
        if (employee != null) {
            return ResponseEntity.badRequest().body("An employee with this email already exists");
        }

        if (!IEmployeeService.isValidUserTypeId(newEmployee.getUserTypeId())) {
            return ResponseEntity.badRequest().body("Invalid user type specified");
        }

        IEmployeeService.createEmployee(newEmployee);
        return ResponseEntity.status(HttpStatus.CREATED).body("Employee created successfully");
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@RequestBody EmployeeDTO newEmployee, @PathVariable Integer id) {
        EmployeeDTO employee = IEmployeeService.getEmployeeByEmail(newEmployee.getMail());
        if (employee != null && !employee.getId().equals(id)) {
            return ResponseEntity.badRequest().build();
        }

        if (!IEmployeeService.isValidUserTypeId(newEmployee.getUserTypeId())) {
            return ResponseEntity.badRequest().build();
        }

        EmployeeDTO employeeUpdated = IEmployeeService.updateEmployee(id, newEmployee);
        if (employeeUpdated != null) {
            return ResponseEntity.ok(employeeUpdated);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees(@RequestParam(required = false) String name) {
        return ResponseEntity.ok(IEmployeeService.getEmployees(name));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Integer id) {
        EmployeeDTO employee = IEmployeeService.getEmployeeById(id);
        if (employee != null) {
            return ResponseEntity.ok(employee);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<EmployeeDTO> deleteEmployee(@PathVariable Integer id) {
        Boolean deleted = IEmployeeService.deleteEmployee(id);
        if (deleted) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
