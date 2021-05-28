package jms.dan.usuarios.service;

import jms.dan.usuarios.exceptions.ApiException;
import jms.dan.usuarios.model.Client;
import jms.dan.usuarios.model.Construction;
import jms.dan.usuarios.model.ConstructionType;
import jms.dan.usuarios.repository.IClientRepository;
import jms.dan.usuarios.repository.IConstructionRepository;
import jms.dan.usuarios.repository.IConstructionTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConstructionService implements IConstructionService {
    final IConstructionRepository constructionRepository;
    final IConstructionTypeRepository constructionTypeRepository;
    final IClientRepository clientRepository;

    @Autowired
    public ConstructionService(IConstructionRepository constructionRepository, IConstructionTypeRepository constructionTypeRepository,
                               IClientRepository clientRepository) {
        this.constructionRepository = constructionRepository;
        this.constructionTypeRepository = constructionTypeRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public void createConstruction(Construction construction) {
        ConstructionType constructionType = constructionTypeRepository.findById(construction.getConstructionTypeId())
                .orElse(null);
        if (constructionType == null) {
            throw new ApiException(HttpStatus.BAD_REQUEST.toString(),
                    "Invalid construction type specified", HttpStatus.BAD_REQUEST.value());
        }
        Client client = clientRepository.findById(construction.getClientId()).orElse(null);
        if (client == null) {
            throw new ApiException(HttpStatus.BAD_REQUEST.toString(),
                    "Invalid client specified", HttpStatus.BAD_REQUEST.value());
        }
        construction.setClient(client);
        construction.setType(constructionType);

        constructionRepository.save(construction);
    }

    @Override
    public void deleteConstruction(Integer id) {

        Construction construction = constructionRepository.findById(id).orElse(null);
        if (construction == null) {
            throw new ApiException(HttpStatus.NOT_FOUND.toString(), "Construction not found", HttpStatus.NOT_FOUND.value());
        }
        constructionRepository.deleteById(id);
    }

    @Override
    public void deleteConstructionsByClient(Client client) {

        List<Construction> constructions = constructionRepository.findConstructionByClient(client);
        if (!constructions.isEmpty()) {
            for (Construction c : constructions) {
                deleteConstruction(c.getId());
            }
        }

    }

    @Override
    public List<Construction> getConstructions(Integer clientId, Integer constructionTypeId) {
//        return constructionRepository.getConstructions(clientId, constructionTypeId);
        return null;
    }

    @Override
    public Construction getConstructionById(Integer id) {
        Construction construction = constructionRepository.findById(id).orElse(null);
        if (construction == null) {
            throw new ApiException(HttpStatus.NOT_FOUND.toString(), "Construction not found", HttpStatus.NOT_FOUND.value());
        }
        return construction;
    }

    @Override
    public Construction updateConstruction(Integer id, Construction construction) {
        // NOT IMPLEMENTED YET
//        TODO CORREGIR!
//        Construction constructionToUpdate = repositoryConstruction.getConstructionById(id);
//        if (constructionToUpdate == null) {
//            throw new ApiException(HttpStatus.NOT_FOUND.toString(), "Construction not found", HttpStatus.NOT_FOUND.value());
//        }
//        return repositoryConstruction.updateConstruction(id, construction);ç
        return null;
    }
}
