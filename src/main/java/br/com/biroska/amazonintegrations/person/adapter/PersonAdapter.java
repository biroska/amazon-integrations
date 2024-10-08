package br.com.biroska.amazonintegrations.person.adapter;

import br.com.biroska.amazonintegrations.integration.database.model.ContactEntity;
import br.com.biroska.amazonintegrations.integration.database.model.PersonEntity;
import br.com.biroska.amazonintegrations.person.model.Contact;
import br.com.biroska.amazonintegrations.person.model.Person;
import br.com.biroska.amazonintegrations.util.ConverterUtils;
import org.apache.commons.collections4.CollectionUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PersonAdapter {

    public static Person adapt(PersonEntity person) {

        LocalDate birthDate = ConverterUtils.stringToLocalDate(person.getBirthdate());

        List<Contact> contacts = ContactAdapter.adapt(person.getContacts());

        return new Person(person.getId(), person.getName(), Integer.parseInt(person.getAge()), birthDate, person.getGender(), contacts );
    }

    public static List<Person> adapt(List<PersonEntity> persons) {

        if (CollectionUtils.isEmpty( persons ) ){
            return new ArrayList<>();
        }

        return persons.stream().map( PersonAdapter::adapt ).toList();
    }


    public static PersonEntity adapt(Person person) {

        List<ContactEntity> contactEntities = ContactAdapter.adaptContacts(person.contacts());

        return PersonEntity.builder()
               .id(person.id() )
               .name( person.name() )
               .age( String.valueOf( person.age() ) )
               .birthdate( ConverterUtils.dateToString(person.birthdate()) )
               .gender( person.gender() )
                .contacts( contactEntities )
               .build();
    }
}
