package br.com.biroska.amazonintegrations.person.service;

import br.com.biroska.amazonintegrations.person.model.Person;

import java.util.List;

public interface PersonService {

    Person save(Person person);

    List<Person> findAll();

    Person update(Person person);

    Boolean delete(String personId);
}
