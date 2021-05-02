package jms.dan.usuarios.controller;

import jms.dan.usuarios.domain.Client;
import jms.dan.usuarios.service.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import jms.dan.usuarios.exceptions.ApiError;
import jms.dan.usuarios.exceptions.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@RestController
@RequestMapping("/clients")
public class ClientController {
    @Autowired
    private IClientService iClientService;

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getClientById(@PathVariable Integer id){
        try {
            Client client = iClientService.getClientById(id);
            return ResponseEntity.ok(client);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getReason());
        }
    }

    @GetMapping(path = "/cuit/{cuit}")
    public ResponseEntity<?> getClientByCuit(@PathVariable String cuit){
        try {
            Client client = iClientService.getClientByCuit(cuit);
            return ResponseEntity.ok(client);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getReason());
        }
    }

    @GetMapping
    public ResponseEntity<List<Client>> getAllClients(@RequestParam(required = false) String businessName){
        return ResponseEntity.ok(iClientService.getClients(businessName));
    }

    @PostMapping
    public ResponseEntity<String> createClient(@RequestBody Client newClient) {
        if (newClient.getConstructions() == null || newClient.getConstructions().size() == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You must specify at least one construction");
        }
        if (newClient.getUser() == null || newClient.getUser().getPassword() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid user information specified");
        }

        try {
            iClientService.createClient(newClient);
            return ResponseEntity.status(HttpStatus.CREATED).body("Client created successfully");
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getReason());
        }
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<?> updateClient(@RequestBody Client newClient, @PathVariable Integer id) {
        try {
            Client clientUpdated = iClientService.updateClient(id, newClient);
            return ResponseEntity.ok(clientUpdated);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getReason());
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteClient(@PathVariable Integer id){
        try {
            iClientService.deleteClient(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Client deleted successfully");
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getReason());
        }
    }
}
