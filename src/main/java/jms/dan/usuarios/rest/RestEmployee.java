package jms.dan.usuarios.rest;

import jms.dan.usuarios.dto.EmployeeDTO;
import jms.dan.usuarios.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class RestEmployee {
    @Autowired
    private IEmployeeService IEmployeeService;

    @PostMapping
    public ResponseEntity<String> createEmployee(@RequestBody EmployeeDTO newEmployee) {
        try {
            IEmployeeService.createEmployee(newEmployee);
            return ResponseEntity.status(HttpStatus.CREATED).body("Employee created successfully");
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getReason());
        }
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<?> updateEmployee(@RequestBody EmployeeDTO newEmployee, @PathVariable Integer id) {
        try {
            EmployeeDTO employeeUpdated = IEmployeeService.updateEmployee(id, newEmployee);
            return ResponseEntity.ok(employeeUpdated);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getReason());
        }
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees(@RequestParam(required = false) String name) {
        return ResponseEntity.ok(IEmployeeService.getEmployees(name));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable Integer id) {
        try {
            EmployeeDTO employee = IEmployeeService.getEmployeeById(id);
            return ResponseEntity.ok(employee);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getReason());
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Integer id) {
        try {
            IEmployeeService.deleteEmployee(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Employee deleted successfully");
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getReason());
        }
    }
}
