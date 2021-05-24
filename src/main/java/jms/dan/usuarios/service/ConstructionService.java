package jms.dan.usuarios.service;

import jms.dan.usuarios.model.Construction;
import jms.dan.usuarios.exceptions.ApiException;
import jms.dan.usuarios.repository.RepositoryConstruction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ConstructionService implements IConstructionService {
    final RepositoryConstruction repositoryConstruction;

    @Autowired
    public ConstructionService(RepositoryConstruction repositoryConstruction) {
        this.repositoryConstruction = repositoryConstruction;
    }

    @Override
    public void createConstruction(Construction construction) {
        repositoryConstruction.createConstruction(construction);
    }

    @Override
    public void deleteConstruction(Integer id) {
        Construction construction = repositoryConstruction.getConstructionById(id);
        if (construction == null) {
            throw new ApiException(HttpStatus.NOT_FOUND.toString(), "Construction not found", HttpStatus.NOT_FOUND.value());
        }
        repositoryConstruction.deleteConstruction(id);
    }

    @Override
    public List<Construction> getConstructions(Integer clientId, Integer constructionTypeId) {
        return repositoryConstruction.getConstructions(clientId, constructionTypeId);
    }

    @Override
    public Construction getConstructionById(Integer id) {
        Construction construction = repositoryConstruction.getConstructionById(id);
        if (construction == null) {
            throw new ApiException(HttpStatus.NOT_FOUND.toString(), "Construction not found", HttpStatus.NOT_FOUND.value());
        }
        return construction;
    }

    @Override
    public Construction updateConstruction(Integer id, Construction construction) {
        Construction constructionToUpdate = repositoryConstruction.getConstructionById(id);
        if (constructionToUpdate == null) {
            throw new ApiException(HttpStatus.NOT_FOUND.toString(), "Construction not found", HttpStatus.NOT_FOUND.value());
        }
        return repositoryConstruction.updateConstruction(id, construction);
    }
}
