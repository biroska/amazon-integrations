package br.com.biroska.amazonintegrations.person.adapter;

import br.com.biroska.amazonintegrations.person.model.Contact;
import br.com.biroska.amazonintegrations.person.model.Person;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PersonApiAdapter {

    private static final Logger logger = LoggerFactory.getLogger(PersonApiAdapter.class);

    public static Person adapt(br.com.biroska.amazonintegrations.model.Person person) {

        LocalDate birthDate =person.getBirthdate();
        List<Contact> contacts = ContactApiAdapter.adaptApiList( person.getContact() );

        return new Person(person.getId(), person.getName(), person.getAge(), birthDate , person.getGender().getValue(), contacts );
    }

    public static br.com.biroska.amazonintegrations.model.Person adapt(Person person) {


        br.com.biroska.amazonintegrations.model.Person apiPerson = new br.com.biroska.amazonintegrations.model.Person();

        return apiPerson.id( person.id() )
                .name( person.name() )
                .age( person.age() )
                .birthdate( person.birthdate() )
                .gender(br.com.biroska.amazonintegrations.model.Person.GenderEnum.fromValue( person.gender() ) )
                .contact( ContactApiAdapter.adapt( person.contacts() ) );
    }

    public static List<br.com.biroska.amazonintegrations.model.Person> adapt(List<Person> persons) {

        if (CollectionUtils.isEmpty( persons ) ){
            return new ArrayList<>();
        }

        return persons.stream().map( PersonApiAdapter::adapt ).toList();
    }
}
