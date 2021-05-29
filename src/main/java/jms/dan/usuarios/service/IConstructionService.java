package jms.dan.usuarios.service;

import jms.dan.usuarios.model.Client;
import jms.dan.usuarios.model.Construction;

import java.util.List;

public interface IConstructionService {
    void createConstruction(Construction construction);

    void deleteConstruction(Integer id);

    void deleteConstructionsByClient(Client client);

    List<Construction> getConstructions(Integer clientId, Integer constructionTypeId);

    Construction getConstructionById(Integer id);

    Construction updateConstruction(Integer id, Construction construction);

}
