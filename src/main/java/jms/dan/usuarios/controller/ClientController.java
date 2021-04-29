package jms.dan.usuarios.controller;

import jms.dan.usuarios.domain.Client;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/clients")
public class ClientController {
    private static final List<Client> clientsList = new ArrayList<>();
    private static Integer ID_GEN = 1;

    @GetMapping(path = "/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable Integer id){
        Optional<Client> client = clientsList
                .stream()
                .filter(cli -> cli.getId().equals(id))
                .findFirst();
        return ResponseEntity.of(client);
    }

    @GetMapping(path = "/cuit/{cuit}")
    public ResponseEntity<Client> getClientByCuit(@PathVariable String cuit){
        Optional<Client> client = clientsList
                .stream()
                .filter(cli -> cli.getCuit().equals(cuit))
                .findFirst();
        return ResponseEntity.of(client);
    }

    @GetMapping(path = "/businessName")
    public ResponseEntity<Stream<Client>> getClientByBusinessName(@RequestParam(required = false) String businessName) {
        if (businessName != null) {
            return ResponseEntity.ok(clientsList.stream().filter(client -> client.getBusinessName().equalsIgnoreCase(businessName)));
        }
        return ResponseEntity.ok(clientsList.stream());
    }

    @GetMapping
    public ResponseEntity<List<Client>> getAllClients(){
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
        if (indexOpt.isPresent()){
            clientsList.set(indexOpt.getAsInt(), newClient);
            return ResponseEntity.ok(newClient);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Client> deleteClient(@PathVariable Integer id){
        OptionalInt indexOpt = IntStream.range(0, clientsList.size()).filter(i -> clientsList.get(i).getId().equals(id)).findFirst();

        if (indexOpt.isPresent()) {
            clientsList.remove(indexOpt.getAsInt());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
