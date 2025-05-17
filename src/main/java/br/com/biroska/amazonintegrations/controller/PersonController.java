package br.com.biroska.amazonintegrations.controller;

import br.com.biroska.amazonintegrations.api.PersonApi;
import br.com.biroska.amazonintegrations.integration.aws.sqs.SqsMessageService;
import br.com.biroska.amazonintegrations.logging.LogMethod;
import br.com.biroska.amazonintegrations.model.Contact;
import br.com.biroska.amazonintegrations.model.Person;
import br.com.biroska.amazonintegrations.person.adapter.PersonApiAdapter;
import br.com.biroska.amazonintegrations.person.service.ContactService;
import br.com.biroska.amazonintegrations.person.service.PersonService;
import br.com.biroska.amazonintegrations.util.ConverterUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/aws-integration")
public class PersonController implements PersonApi {

    private final PersonService personService;

    private final ContactService contactService;

    private final SqsMessageService sqsService;

    @Override
    @LogMethod
    public ResponseEntity<List<Person>> listPerson() {
        List<Person> adapted = PersonApiAdapter.adapt(personService.findAll());
        return ResponseEntity.ofNullable( adapted );
    }

    @Override
    @LogMethod
    public ResponseEntity<Person> savePerson( Person person) {
        sqsService.sendMessage(ConverterUtils.toJson( person ) );
        return ResponseEntity.ofNullable( person );
    }
    @Override
    @LogMethod
    public ResponseEntity<Person> updatePerson( Person person) {

        br.com.biroska.amazonintegrations.person.model.Person personModel = PersonApiAdapter.adapt(person);
        br.com.biroska.amazonintegrations.person.model.Person update = personService.update( personModel );
        Person adapted = PersonApiAdapter.adapt(update);

        return ResponseEntity.ofNullable( adapted );
    }

    @Override
    @LogMethod
    public ResponseEntity<List<Contact>> updatePersonContact(String personId, Contact contact) {

        return ResponseEntity.ofNullable( contactService.addContact(personId, contact) );
    }

    @Override
    @LogMethod
    public ResponseEntity<Void> deletePerson( String personId ) {
        personService.delete(personId);
        return ResponseEntity.ok().build();
    }
}
