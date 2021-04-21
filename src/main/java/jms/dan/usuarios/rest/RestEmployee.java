package jms.dan.usuarios.rest;

import jms.dan.usuarios.dto.EmployeeDTO;
import jms.dan.usuarios.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class RestEmployee {
    @Autowired
    private IEmployeeService IEmployeeService;

    @PostMapping
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody EmployeeDTO newEmployee) {
        return ResponseEntity.ok(IEmployeeService.createEmployee(newEmployee));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@RequestBody EmployeeDTO newEmployee, @PathVariable Integer id) {
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
