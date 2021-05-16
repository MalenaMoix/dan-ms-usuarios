package jms.dan.usuarios.repository;

import jms.dan.usuarios.domain.Construction;
import java.util.List;

public interface IRepositoryConstruction {
    void createConstruction(Construction newConstruction);

    Construction updateConstruction(Integer id, Construction newConstruction);

    void deleteConstruction(Integer id);

    Construction getConstructionById(Integer id);
    List<Construction> getConstructions(Integer clientId, Integer constructionTypeId);
}
