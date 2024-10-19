package br.com.biroska.amazonintegrations.person.service;

import br.com.biroska.amazonintegrations.person.model.Contact;

public interface ContactService {

    Contact save(Contact contact);

    Contact update(Contact contact);

    Boolean delete(Contact contact);
}
