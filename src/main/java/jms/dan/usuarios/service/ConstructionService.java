package jms.dan.usuarios.service;

import jms.dan.usuarios.domain.Construction;
import jms.dan.usuarios.exceptions.ApiException;
import jms.dan.usuarios.repository.IRepositoryConstruction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ConstructionService implements IConstructionService {
    @Autowired
    private IRepositoryConstruction iRepositoryConstruction;

    @Override
    public void createConstruction(Construction construction) {
        iRepositoryConstruction.createConstruction(construction);
    }

    @Override
    public void deleteConstruction(Integer id) {
        Construction construction = iRepositoryConstruction.getConstructionById(id);
        if (construction == null) {
            throw new ApiException(HttpStatus.NOT_FOUND.toString(), "Construction not found", HttpStatus.NOT_FOUND.value());
        }
        iRepositoryConstruction.deleteConstruction(id);
    }

    @Override
    public List<Construction> getConstructions(Integer clientId, Integer constructionTypeId) {
        return iRepositoryConstruction.getConstructions(clientId, constructionTypeId);
    }

    @Override
    public Construction getConstructionById(Integer id) {
        Construction construction = iRepositoryConstruction.getConstructionById(id);
        if (construction == null) {
            throw new ApiException(HttpStatus.NOT_FOUND.toString(), "Construction not found", HttpStatus.NOT_FOUND.value());
        }
        return construction;
    }

    @Override
    public Construction updateConstruction(Integer id, Construction construction) {
        Construction constructionToUpdate = iRepositoryConstruction.getConstructionById(id);
        if (constructionToUpdate == null) {
            throw new ApiException(HttpStatus.NOT_FOUND.toString(), "Construction not found", HttpStatus.NOT_FOUND.value());
        }
        return iRepositoryConstruction.updateConstruction(id, construction);
    }
}
