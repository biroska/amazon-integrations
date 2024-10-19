package br.com.biroska.amazonintegrations.person.service.impl;

import br.com.biroska.amazonintegrations.integration.database.facade.PersonFacade;
import br.com.biroska.amazonintegrations.integration.database.model.PersonEntity;
import br.com.biroska.amazonintegrations.person.adapter.PersonAdapter;
import br.com.biroska.amazonintegrations.person.model.Person;
import br.com.biroska.amazonintegrations.person.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonFacade service;

    @Override
    public List<Person> findAll() {
        List<PersonEntity> persons = service.findAll();

        return PersonAdapter.adapt(persons);
    }

    @Override
    public Person save(Person person) {

        PersonEntity entity = PersonAdapter.adapt( person );

        PersonEntity saved = service.save(entity);

        return PersonAdapter.adapt( saved );
    }

    @Override
    public Person update(Person person) {

        PersonEntity entity = PersonAdapter.adapt( person );

        PersonEntity saved = service.update(entity);

        return PersonAdapter.adapt( saved );
    }

    @Override
    public Boolean delete(String personId) {
        return service.delete( personId);
    }
}
