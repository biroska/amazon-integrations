package br.com.biroska.amazonintegrations.controller;

import br.com.biroska.amazonintegrations.person.model.Contact;
import br.com.biroska.amazonintegrations.person.model.Person;
import br.com.biroska.amazonintegrations.person.service.ContactService;
import br.com.biroska.amazonintegrations.person.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/aws-integration/person")
public class PersonController {

    private final PersonService personService;

    private final ContactService contactService;

    @GetMapping
    public List<Person> listPerson() {
        return personService.findAll();
    }

    @PostMapping
    public Person savePerson(@RequestBody Person person) {
        return personService.save(person);
    }

    @PutMapping
    public Person updatePerson(@RequestBody Person person) {
        return personService.update(person);
    }

    @PostMapping("/{personId}/contact")
    public List<Contact> updatePersonContact(@PathVariable String personId, @RequestBody Contact contact) {
        return contactService.addContact(personId, contact);
    }

    @DeleteMapping("/{personId}")
    public void deletePerson(@PathVariable String personId) {
        personService.delete(personId);
    }
}
