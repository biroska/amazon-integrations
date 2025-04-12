package br.com.biroska.amazonintegrations.person.adapter;

import br.com.biroska.amazonintegrations.integration.database.model.ContactEntity;
import br.com.biroska.amazonintegrations.person.model.Contact;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ContactApiAdapter {

    private static final Logger logger = LoggerFactory.getLogger(ContactApiAdapter.class);

    public static br.com.biroska.amazonintegrations.model.Contact adapt(Contact contact) {

        br.com.biroska.amazonintegrations.model.Contact apiContact = new br.com.biroska.amazonintegrations.model.Contact();

        return apiContact.name(contact.name()).phone(contact.phone());
    }

    public static List<br.com.biroska.amazonintegrations.model.Contact> adapt(List<Contact> contacts) {

        if (CollectionUtils.isEmpty( contacts ) ){
            return new ArrayList<>();
        }

        return contacts.stream().map( ContactApiAdapter::adapt ).toList();
    }

    public static Contact adapt(br.com.biroska.amazonintegrations.model.Contact contact) {

        return new Contact(contact.getName(), contact.getPhone());
    }

    public static ContactEntity adaptToEntity(br.com.biroska.amazonintegrations.model.Contact contact) {

        return ContactEntity.builder()
                .name( contact.getName() )
                .phone( contact.getPhone() )
                .build();
    }

    public static List<Contact> adaptApiList(List<br.com.biroska.amazonintegrations.model.Contact> contacts) {

        if (CollectionUtils.isEmpty( contacts ) ){
            return new ArrayList<>();
        }

        return contacts.stream().map( ContactApiAdapter::adapt ).toList();
    }

    public static List<br.com.biroska.amazonintegrations.model.Contact> adaptFromEntity(List<ContactEntity> contacts) {

        if (CollectionUtils.isEmpty( contacts ) ){
            return new ArrayList<>();
        }

        return contacts.stream().map( ContactApiAdapter::adapt ).toList();
    }

    public static br.com.biroska.amazonintegrations.model.Contact adapt(ContactEntity contact) {

        return new br.com.biroska.amazonintegrations.model.Contact()
                .name( contact.getName() )
                .phone( contact.getPhone() );
    }
}
