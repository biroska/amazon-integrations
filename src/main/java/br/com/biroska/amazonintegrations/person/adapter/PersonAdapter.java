package br.com.biroska.amazonintegrations.person.adapter;

import br.com.biroska.amazonintegrations.integration.database.model.ContactEntity;
import br.com.biroska.amazonintegrations.integration.database.model.PersonEntity;
import br.com.biroska.amazonintegrations.person.model.Contact;
import br.com.biroska.amazonintegrations.person.model.Person;
import br.com.biroska.amazonintegrations.util.ConverterUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PersonAdapter {

    private static final Logger logger = LoggerFactory.getLogger(PersonAdapter.class);

    public static Person toPerson(String jsonPerson){
        try {
            return ConverterUtils.getMapper().readValue(jsonPerson, Person.class);
        } catch (JsonProcessingException e) {
            logger.error("Ocorreu um erro ao converter o json {} para Person", jsonPerson, e);
        }
        return null;
    }

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
