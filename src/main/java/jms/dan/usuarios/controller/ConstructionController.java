package jms.dan.usuarios.controller;

import jms.dan.usuarios.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/constructions")
public class ConstructionController {

    private static final List<ConstructionType> constructionTypesList = new ArrayList<>();
    private static final List<Construction> constructionsList = new ArrayList<>();
    private static final List<Client> clientsList = new ArrayList<>();
    private static Integer ID_GEN = 1;

    @PostConstruct
    private void init() {
        ConstructionType reform = new ConstructionType(1, "Reforma");
        ConstructionType house = new ConstructionType(2, "Casa");
        ConstructionType building = new ConstructionType(3, "Edificio");
        ConstructionType road = new ConstructionType(4, "Vial");
        constructionTypesList.add(reform);
        constructionTypesList.add(house);
        constructionTypesList.add(building);
        constructionTypesList.add(road);
        UserType clientUserType = new UserType();
        clientUserType.setId(1);
        clientUserType.setType("CLIENT");
        User userOne = new User(0, "pepitoSRL", "123456", clientUserType);
        User userTwo = new User(1, "JorgeRivas", "123456", clientUserType);
        Client clientOne = new Client(0, "Pepito S.R.L", "30-5569331-6", "compras@pepitosrl.com.ar", 1000000.00, true, userOne);
        Client clientTwo = new Client(1, "Jorge Rivas", "22-25684336-7", "rivasjorge@gmail.com", 500000.00, true, userTwo);
        clientsList.add(clientOne);
        clientsList.add(clientTwo);
    }

    @PostMapping
    public ResponseEntity<Construction> createConstruction(@RequestBody Construction newConstruction) {
        ConstructionType type = constructionTypesList.stream().filter(c -> newConstruction.getConstructionTypeId().equals(c.getId()))
                .findAny()
                .orElse(null);
        Client client = clientsList.stream().filter(c -> newConstruction.getClientId().equals(c.getId()))
                .findAny()
                .orElse(null);
        if (type == null || client == null)
            return new ResponseEntity("Wrong construction type or client", HttpStatus.BAD_REQUEST);
        newConstruction.setId(ID_GEN++);
        newConstruction.setType(type);
        newConstruction.setClient(client);
        constructionsList.add(newConstruction);
        client.getConstructions().add(newConstruction);

        return ResponseEntity.ok(newConstruction);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity deleteConstruction(@PathVariable Integer id) {
        OptionalInt indexOpt = IntStream.range(0, constructionsList.size())
                .filter(i -> constructionsList.get(i).getId().equals(id))
                .findFirst();

        if (indexOpt.isPresent()) {
            eliminateAssociationsOfConstruction(constructionsList.get(indexOpt.getAsInt()).getId());
            constructionsList.remove(indexOpt.getAsInt());
            return ResponseEntity.ok(HttpStatus.NO_CONTENT);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Construction>> getConstructions(@RequestParam(required = false) Integer clientId, @RequestParam(required = false) Integer constructionTypeId) {
        // TODO It Should filter by both params at same time
        if (clientId != null || constructionTypeId != null)
            return ResponseEntity.ok(constructionsList.stream().filter(construction -> construction.getConstructionTypeId().equals(constructionTypeId)
                    || construction.getClient().getId().equals(clientId)).collect(Collectors.toList()));
        return ResponseEntity.ok(constructionsList);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Construction> getConstructionById(@PathVariable Integer id) {
        Construction construction = constructionsList.stream().filter(c -> c.getId().equals(id)).findFirst().orElse(null);
        if (construction != null) return ResponseEntity.ok(construction);
        return ResponseEntity.notFound().build();
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Construction> updateConstruction(@PathVariable Integer id, @RequestBody Construction newConstruction) {
        // TODO this should assign one by one the properties to the existing construction and not create a new one
        OptionalInt indexOpt = IntStream.range(0, constructionsList.size())
                .filter(i -> constructionsList.get(i).getId().equals(id))
                .findFirst();
        if (indexOpt.isPresent()) {
            ConstructionType type = constructionTypesList.stream().filter(c -> newConstruction.getConstructionTypeId().equals(c.getId()))
                    .findAny()
                    .orElse(null);
            Client client = clientsList.stream().filter(c -> newConstruction.getClientId().equals(c.getId()))
                    .findAny()
                    .orElse(null);
            if (type == null || client == null)
                return new ResponseEntity("Wrong construction type or client", HttpStatus.BAD_REQUEST);
            if (client.getConstructions().stream().filter(c -> c.getId().equals(newConstruction.getId())).findAny().isEmpty() ) client.getConstructions().add(newConstruction);
            newConstruction.setId(id);
            constructionsList.set(indexOpt.getAsInt(), newConstruction);
            return ResponseEntity.ok(newConstruction);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private void eliminateAssociationsOfConstruction(Integer idConstructionToEliminate) {
        Client clientAssociated = clientsList.stream().filter(
                client -> client.getConstructions().stream()
                        .filter(construction -> construction.getId().
                                equals(idConstructionToEliminate)).findAny().isEmpty()).findFirst().orElse(null);
        if (clientAssociated == null) return;
        clientAssociated.getConstructions().removeIf(construction -> construction.getId().equals(idConstructionToEliminate));
    }
}
