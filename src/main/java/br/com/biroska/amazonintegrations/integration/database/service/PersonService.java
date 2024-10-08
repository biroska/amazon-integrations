package br.com.biroska.amazonintegrations.integration.database.service;

import br.com.biroska.amazonintegrations.integration.database.model.PersonEntity;

import java.util.List;

public interface PersonService {

    PersonEntity save(PersonEntity person);

    List<PersonEntity> findAll();

    PersonEntity update(PersonEntity person);

    Boolean delete(String personId);
}
