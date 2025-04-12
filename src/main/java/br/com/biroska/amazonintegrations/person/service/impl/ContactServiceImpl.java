package br.com.biroska.amazonintegrations.person.service.impl;

import br.com.biroska.amazonintegrations.integration.database.facade.PersonFacade;
import br.com.biroska.amazonintegrations.integration.database.model.ContactEntity;
import br.com.biroska.amazonintegrations.integration.database.model.PersonEntity;
import br.com.biroska.amazonintegrations.model.Contact;
import br.com.biroska.amazonintegrations.person.adapter.ContactApiAdapter;
import br.com.biroska.amazonintegrations.person.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

    private final PersonFacade facade;

    @Override
    public List<Contact> addContact(String personId, Contact contact) {

        ContactEntity entity = ContactApiAdapter.adaptToEntity(contact);

        PersonEntity personEntity = facade.addContact(personId, entity);

        return ContactApiAdapter.adaptFromEntity(personEntity.getContacts());
    }
}
