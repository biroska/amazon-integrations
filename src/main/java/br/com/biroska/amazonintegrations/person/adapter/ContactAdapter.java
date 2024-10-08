package br.com.biroska.amazonintegrations.person.adapter;

import br.com.biroska.amazonintegrations.integration.database.model.ContactEntity;
import br.com.biroska.amazonintegrations.person.model.Contact;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter {

    public static Contact adapt(ContactEntity contactEntity) {

        return new Contact(contactEntity.getName(), contactEntity.getPhone() );
    }

    public static List<Contact> adapt(List<ContactEntity> contacts) {

        if (CollectionUtils.isEmpty( contacts ) ){
            return new ArrayList<>();
        }

        return contacts.stream().map( ContactAdapter::adapt ).toList();
    }

    public static ContactEntity adapt(Contact contact) {

       return ContactEntity.builder()
               .name( contact.name() )
               .phone( contact.phone() )
               .build();
    }


    public static List<ContactEntity> adaptContacts(List<Contact> contacts) {

        if (CollectionUtils.isEmpty( contacts ) ){
            return new ArrayList<>();
        }

        return contacts.stream().map( ContactAdapter::adapt ).toList();
    }
}
