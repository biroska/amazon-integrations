package br.com.biroska.amazonintegrations.controller;

import br.com.biroska.amazonintegrations.person.facade.PersonFacade;
import br.com.biroska.amazonintegrations.person.model.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/aws-integration/person")
public class PersonController {

    private final PersonFacade facade;

    @GetMapping
    public List<Person> listPerson() {
        return facade.findAll();
    }

    @PostMapping
    public Person savePerson(@RequestBody Person person) {
        return facade.save( person );
    }

    @PutMapping
    public Person updatePerson(@RequestBody Person person) {
        return facade.update( person );
    }

    @DeleteMapping("/{personId}")
    public void deletePerson(@PathVariable String personId) {
        facade.delete( personId );
    }
}
