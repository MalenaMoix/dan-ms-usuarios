package jms.dan.usuarios.repository;

import jms.dan.usuarios.model.*;
import jms.dan.usuarios.exceptions.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Repository
public class RepositoryConstruction implements IRepositoryConstruction {
    private static final List<ConstructionType> constructionTypesList = new ArrayList<>();
    private static final List<Construction> constructionsList = new ArrayList<>();
    private static Integer ID_GEN = 1;

    final RepositoryClient repositoryClient;

    @Autowired
    public RepositoryConstruction(RepositoryClient repositoryClient) {
        this.repositoryClient = repositoryClient;
    }

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
    }

    @Override
    public void createConstruction(Construction newConstruction) {
        ConstructionType type = constructionTypesList.stream().filter(c -> newConstruction.getConstructionTypeId().equals(c.getId()))
                .findAny()
                .orElse(null);
        Client client = repositoryClient.getAllClients().stream().filter(c -> newConstruction.getClientId().equals(c.getId()))
                .findAny()
                .orElse(null);
        if (type == null || client == null) {
            throw new ApiException(HttpStatus.BAD_REQUEST.toString(), "Wrong construction type or client", HttpStatus.BAD_REQUEST.value());
        }
        newConstruction.setId(ID_GEN++);
        newConstruction.setType(type);
        newConstruction.setClient(client);
        constructionsList.add(newConstruction);

        List<Construction> clientConstructions = getConstructions(client.getId(), null);
        clientConstructions.add(newConstruction);
        client.setConstructions(clientConstructions);
    }

    @Override
    public Construction updateConstruction(Integer id, Construction newConstruction) {
        OptionalInt indexOpt = IntStream.range(0, constructionsList.size())
                .filter(i -> constructionsList.get(i).getId().equals(id))
                .findFirst();
        if (indexOpt.isPresent()) {
            ConstructionType type = constructionTypesList.stream().filter(c -> newConstruction.getConstructionTypeId().equals(c.getId()))
                    .findAny()
                    .orElse(null);
            Client client = repositoryClient.getAllClients().stream().filter(c -> newConstruction.getClientId().equals(c.getId()))
                    .findAny()
                    .orElse(null);
            if (type == null || client == null) {
                throw new ApiException(HttpStatus.BAD_REQUEST.toString(), "Wrong construction type or client", HttpStatus.BAD_REQUEST.value());
            }

            List<Construction> clientConstructions = getConstructions(client.getId(), null);
            if (clientConstructions.stream().filter(c -> c.getId().equals(newConstruction.getId())).findAny().isEmpty()) {
                clientConstructions.add(newConstruction);
                client.setConstructions(clientConstructions);
            }
            newConstruction.setId(id);
            newConstruction.setType(type);
            newConstruction.setClient(client);
            constructionsList.set(indexOpt.getAsInt(), newConstruction);
            return newConstruction;
        }
        return null;
    }

    @Override
    public void deleteConstruction(Integer id) {
        OptionalInt indexOpt = IntStream.range(0, constructionsList.size())
                .filter(i -> constructionsList.get(i).getId().equals(id))
                .findFirst();

        if (indexOpt.isPresent()) {
            deleteAssociationsOfConstruction(constructionsList.get(indexOpt.getAsInt()).getId());
            constructionsList.remove(indexOpt.getAsInt());
        }
    }

    @Override
    public Construction getConstructionById(Integer id) {
        return constructionsList.stream().filter(c -> c.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public List<Construction> getConstructions(Integer clientId, Integer constructionTypeId) {
        // TODO It Should filter by both params at same time
        if (clientId != null || constructionTypeId != null)
            return constructionsList.stream().filter(construction -> construction.getConstructionTypeId().equals(constructionTypeId)
                    || construction.getClient().getId().equals(clientId)).collect(Collectors.toList());
        return constructionsList;
    }

    private void deleteAssociationsOfConstruction(Integer idConstructionToDelete) {
        Client clientAssociated = repositoryClient.getAllClients().stream().filter(
                client -> getConstructions(client.getId(), null).stream()
                        .filter(construction -> construction.getId().
                                equals(idConstructionToDelete)).findAny().isEmpty()).findFirst().orElse(null);
        if (clientAssociated == null) return;
        clientAssociated.getConstructions().removeIf(construction -> construction.getId().equals(idConstructionToDelete));
    }
}
