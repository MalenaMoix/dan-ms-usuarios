package jms.dan.usuarios.controller;

import jms.dan.usuarios.domain.*;
import jms.dan.usuarios.exceptions.ApiError;
import jms.dan.usuarios.exceptions.ApiException;
import jms.dan.usuarios.service.ConstructionService;
import jms.dan.usuarios.service.IConstructionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/constructions")
public class ConstructionController {
    final ConstructionService constructionService;

    @Autowired
    public ConstructionController(ConstructionService constructionService) {
        this.constructionService = constructionService;
    }

    @PostMapping
    public ResponseEntity<?> createConstruction(@RequestBody Construction newConstruction) {
        try {
            constructionService.createConstruction(newConstruction);
            return ResponseEntity.status(HttpStatus.CREATED).body("Construction created successfully");
        } catch (ApiException e) {
            return new ResponseEntity<>(
                    new ApiError(e.getCode(), e.getDescription(), e.getStatusCode()),
                    HttpStatus.valueOf(e.getStatusCode()));
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteConstruction(@PathVariable Integer id) {
        try {
            constructionService.deleteConstruction(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Construction deleted successfully");
        } catch (ApiException e) {
            return new ResponseEntity<>(
                    new ApiError(e.getCode(), e.getDescription(), e.getStatusCode()),
                    HttpStatus.valueOf(e.getStatusCode()));
        }
    }

    @GetMapping
    public ResponseEntity<List<Construction>> getConstructions(@RequestParam(required = false) Integer clientId, @RequestParam(required = false) Integer constructionTypeId) {
        return ResponseEntity.ok(constructionService.getConstructions(clientId, constructionTypeId));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getConstructionById(@PathVariable Integer id) {
        try {
            Construction construction = constructionService.getConstructionById(id);
            return new ResponseEntity<>(construction, HttpStatus.OK);
        } catch (ApiException e) {
            return new ResponseEntity<>(
                    new ApiError(e.getCode(), e.getDescription(), e.getStatusCode()),
                    HttpStatus.valueOf(e.getStatusCode()));
        }
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<?> updateConstruction(@PathVariable Integer id, @RequestBody Construction newConstruction) {
        try {
            Construction construction = constructionService.updateConstruction(id, newConstruction);
            return ResponseEntity.ok(construction);
        } catch (ApiException e) {
            return new ResponseEntity<>(
                    new ApiError(e.getCode(), e.getDescription(), e.getStatusCode()),
                    HttpStatus.valueOf(e.getStatusCode()));
        }
    }
}
