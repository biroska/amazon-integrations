package br.com.biroska.amazonintegrations.person.facade;

import br.com.biroska.amazonintegrations.person.model.Person;

import java.util.List;

public interface PersonFacade {

    Person save(Person person);

    List<Person> findAll();

    Person update(Person person);

    Boolean delete(String personId);
}
