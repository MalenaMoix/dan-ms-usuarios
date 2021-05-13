package jms.dan.usuarios.controller;

import jms.dan.usuarios.domain.Client;
import jms.dan.usuarios.exceptions.ApiError;
import jms.dan.usuarios.exceptions.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    private static final List<Client> clientsList = new ArrayList<>();
    private static Integer ID_GEN = 1;

    @GetMapping(path = "/{id}")
    public ResponseEntity getClientById(@PathVariable Integer id) {
        try {
            Client client = clientsList
                    .stream()
                    .filter(cli -> cli.getId().equals(id))
                    .findFirst().orElse(null);
            if (client == null) return new ResponseEntity(
                    new ApiError(HttpStatus.NOT_FOUND.toString(), "Client id "+id+" does not exist.", HttpStatus.NOT_FOUND.value()),
                    HttpStatus.NOT_FOUND);
            return new ResponseEntity(client, HttpStatus.OK);
        } catch (ApiException e) {
            return new ResponseEntity(
                    new ApiError(e.getCode(), e.getDescription(), e.getStatusCode()),
                    HttpStatus.valueOf(e.getStatusCode()));
        }
    }

    @GetMapping(path = "/cuit/{cuit}")
    public ResponseEntity<Client> getClientByCuit(@PathVariable String cuit) {
        Optional<Client> client = clientsList
                .stream()
                .filter(cli -> cli.getCuit().equals(cuit))
                .findFirst();
        return ResponseEntity.of(client);
    }

    @GetMapping
    public ResponseEntity<List<Client>> getClients(@RequestParam(required = false) String businessName) {
        if (businessName != null) {
            return ResponseEntity.ok(clientsList.stream().filter(client -> client.getBusinessName().equalsIgnoreCase(businessName)).collect(Collectors.toList()));
        }
        return ResponseEntity.ok(clientsList);
    }

    @PostMapping
    public ResponseEntity<Client> createClient(@RequestBody Client newClient) {
        newClient.setId(ID_GEN++);
        clientsList.add(newClient);
        return ResponseEntity.ok(newClient);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Client> updateClient(@RequestBody Client newClient, @PathVariable Integer id) {
        OptionalInt indexOpt = IntStream.range(0, clientsList.size())
                .filter(i -> clientsList.get(i).getId().equals(id))
                .findFirst();
        if (indexOpt.isPresent()) {
            clientsList.set(indexOpt.getAsInt(), newClient);
            return ResponseEntity.ok(newClient);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Client> deleteClient(@PathVariable Integer id) {
        OptionalInt indexOpt = IntStream.range(0, clientsList.size()).filter(i -> clientsList.get(i).getId().equals(id)).findFirst();

        if (indexOpt.isPresent()) {
            clientsList.remove(indexOpt.getAsInt());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
