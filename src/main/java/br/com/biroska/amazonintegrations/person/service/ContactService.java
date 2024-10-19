package br.com.biroska.amazonintegrations.person.service;

import br.com.biroska.amazonintegrations.person.model.Contact;

import java.util.List;

public interface ContactService {

    List<Contact> addContact(String personId, Contact contact);

    Contact update(Contact contact);

    Boolean delete(Contact contact);
}
