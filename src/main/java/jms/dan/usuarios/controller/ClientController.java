package jms.dan.usuarios.controller;

import jms.dan.usuarios.domain.Client;
import jms.dan.usuarios.dto.ClientDTO;
import jms.dan.usuarios.exceptions.ApiError;
import jms.dan.usuarios.exceptions.ApiException;
import jms.dan.usuarios.service.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {
    @Autowired
    private IClientService iClientService;

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getClientById(@PathVariable Integer id){
        try {
            Client client = iClientService.getClientById(id);
            return new ResponseEntity<>(client, HttpStatus.OK);
        } catch (ApiException e) {
            return new ResponseEntity<>(
                    new ApiError(e.getCode(), e.getDescription(), e.getStatusCode()),
                    HttpStatus.valueOf(e.getStatusCode()));
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
    public ResponseEntity<List<Client>> getClients(@RequestParam(required = false) String businessName){
        return ResponseEntity.ok(iClientService.getClients(businessName));
    }

    @PostMapping
    public ResponseEntity<String> createClient(@RequestBody ClientDTO clientDTO) {
        // TODO check if it's a list before doing .size()
        if (clientDTO.getConstructions() == null || clientDTO.getConstructions().size() == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You must specify at least one construction");
        }
        if (clientDTO.getConstructions() != null && clientDTO.getConstructions().size() > 0 &&
                (clientDTO.getConstructions().get(0).getClientId() == null || clientDTO.getConstructions().get(0).getConstructionTypeId() == null)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You must specify a client and a construction type");
        }
        if (clientDTO.getUser() == null || clientDTO.getUser().getPassword() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid user information specified");
        }

        try {
            iClientService.createClient(clientDTO);
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
