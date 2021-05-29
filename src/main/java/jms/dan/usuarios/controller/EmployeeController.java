package jms.dan.usuarios.controller;

import jms.dan.usuarios.model.Employee;
import jms.dan.usuarios.exceptions.ApiError;
import jms.dan.usuarios.exceptions.ApiException;
import jms.dan.usuarios.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<?> createEmployee(@RequestBody Employee newEmployee) {
        if (newEmployee.getUser() == null || newEmployee.getUser().getPassword() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid user information specified");
        }

        try {
            employeeService.createEmployee(newEmployee);
            return ResponseEntity.status(HttpStatus.CREATED).body("Employee created successfully");
        } catch (ApiException e) {
            return new ResponseEntity<>(
                    new ApiError(e.getCode(), e.getDescription(), e.getStatusCode()),
                    HttpStatus.valueOf(e.getStatusCode()));
        }
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<?> updateEmployee(@RequestBody Employee newEmployee, @PathVariable Integer id) {
        if (newEmployee.getUser() == null || newEmployee.getUser().getPassword() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid user information specified");
        }

        try {
            Employee employeeUpdated = employeeService.updateEmployee(id, newEmployee);
            return ResponseEntity.ok(employeeUpdated);
        } catch (ApiException e) {
            return new ResponseEntity<>(
                    new ApiError(e.getCode(), e.getDescription(), e.getStatusCode()),
                    HttpStatus.valueOf(e.getStatusCode()));
        }
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees(@RequestParam(required = false) String name) {
        return ResponseEntity.ok(employeeService.getEmployees(name));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable Integer id) {
        try {
            Employee employee = employeeService.getEmployeeById(id);
            return ResponseEntity.ok(employee);
        } catch (ApiException e) {
            return new ResponseEntity<>(
                    new ApiError(e.getCode(), e.getDescription(), e.getStatusCode()),
                    HttpStatus.valueOf(e.getStatusCode()));
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Integer id) {
        try {
            employeeService.deleteEmployee(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Employee deleted successfully");
        } catch (ApiException e) {
            return new ResponseEntity<>(
                    new ApiError(e.getCode(), e.getDescription(), e.getStatusCode()),
                    HttpStatus.valueOf(e.getStatusCode()));
        }
    }
}
