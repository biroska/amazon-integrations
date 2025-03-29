package br.com.biroska.amazonintegrations.controller;

import br.com.biroska.amazonintegrations.api.PersonApi;
import br.com.biroska.amazonintegrations.integration.aws.sqs.SqsMessageService;
import br.com.biroska.amazonintegrations.logging.LogMethod;
import br.com.biroska.amazonintegrations.model.Person;
import br.com.biroska.amazonintegrations.person.adapter.PersonApiAdapter;
import br.com.biroska.amazonintegrations.person.model.Contact;
import br.com.biroska.amazonintegrations.person.service.ContactService;
import br.com.biroska.amazonintegrations.person.service.PersonService;
import br.com.biroska.amazonintegrations.util.ConverterUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/aws-integration/person")
public class PersonController implements PersonApi {

    private final PersonService personService;

    private final ContactService contactService;

    private final SqsMessageService sqsService;

    @LogMethod
    @GetMapping
    public ResponseEntity<List<Person>> listPerson() {
        List<Person> adapted = PersonApiAdapter.adapt(personService.findAll());
        return ResponseEntity.ofNullable( adapted );
    }

    @LogMethod
    @PostMapping
    public ResponseEntity<Person> savePerson(@RequestBody @Valid Person person) {
        sqsService.sendMessage(ConverterUtils.toJson( person ) );
        return ResponseEntity.ofNullable( person );
    }

    @LogMethod
    @PutMapping
    public ResponseEntity<Person> updatePerson(@RequestBody Person person) {

        br.com.biroska.amazonintegrations.person.model.Person personModel = PersonApiAdapter.adapt(person);
        br.com.biroska.amazonintegrations.person.model.Person update = personService.update( personModel );
        Person adapted = PersonApiAdapter.adapt(update);

        return ResponseEntity.ofNullable( adapted );
    }

    @LogMethod
    @PostMapping("/{personId}/contact")
    public ResponseEntity<List<Contact>> updatePersonContact(@PathVariable String personId, @RequestBody Contact contact) {
        return ResponseEntity.ofNullable( contactService.addContact(personId, contact) );
    }

    @LogMethod
    @DeleteMapping("/{personId}")
    public ResponseEntity<Void> deletePerson(@PathVariable String personId) {
        personService.delete(personId);
        return ResponseEntity.ok().build();
    }
}
