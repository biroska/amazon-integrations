package br.com.biroska.amazonintegrations.integration.database.facade.impl;

import br.com.biroska.amazonintegrations.integration.database.facade.PersonFacade;
import br.com.biroska.amazonintegrations.integration.database.model.ContactEntity;
import br.com.biroska.amazonintegrations.integration.database.model.PersonEntity;
import br.com.biroska.amazonintegrations.integration.database.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonFacadeImpl implements PersonFacade {

    private final PersonRepository repository;

    @Override
    public List<PersonEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public PersonEntity save(PersonEntity person) {
        return repository.save( person );
    }

    @Override
    public PersonEntity update(PersonEntity person) {

        Optional<PersonEntity> personEntity = repository.findById(person.getId());

        personEntity.ifPresent( entity -> entity.updateFields( person ) );
        return personEntity.map(repository::save).orElseGet(PersonEntity::new);
    }

    @Override
    public Boolean delete(String personId) {
        repository.deleteById(personId);

        return Boolean.TRUE;
    }

    @Override
    public PersonEntity addContact(String personId, ContactEntity contactEntity) {

        Optional<PersonEntity> personEntity = repository.findById( personId );

        if (personEntity.isPresent()){
            PersonEntity person = personEntity.get();
            person.getContacts().add( contactEntity );

            return this.save( person );
        }
        return null;
    }
}
