package br.com.biroska.amazonintegrations.person.service.impl;

import br.com.biroska.amazonintegrations.integration.database.facade.PersonFacade;
import br.com.biroska.amazonintegrations.integration.database.model.ContactEntity;
import br.com.biroska.amazonintegrations.integration.database.model.PersonEntity;
import br.com.biroska.amazonintegrations.person.adapter.ContactAdapter;
import br.com.biroska.amazonintegrations.person.service.ContactService;
import br.com.biroska.amazonintegrations.person.model.Contact;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

    private final PersonFacade facade;

    @Override
    public List<Contact> addContact(String personId, Contact contact) {

        ContactEntity entity = ContactAdapter.adapt(contact);

        PersonEntity personEntity = facade.addContact(personId, entity);
        return ContactAdapter.adapt(personEntity.getContacts());
    }

    @Override
    public Contact update(Contact contact) {
        return null;
    }

    @Override
    public Boolean delete(Contact contact) {
        return null;
    }
}
