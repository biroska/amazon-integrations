package br.com.biroska.amazonintegrations.integration.database.facade;

import br.com.biroska.amazonintegrations.integration.database.model.PersonEntity;

import java.util.List;

public interface PersonFacade {

    PersonEntity save(PersonEntity person);

    List<PersonEntity> findAll();

    PersonEntity update(PersonEntity person);

    Boolean delete(String personId);
}
